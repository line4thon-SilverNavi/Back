package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramNotFoundException;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes.PageInfo;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.likelion._thon.silver_navi.global.util.geo.UpdateImagesUtils.updateImageFiles;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final NursingFacilityRepository nursingFacilityRepository;

    private final S3Service s3Service;

    @Override
    @Transactional
    public void programCreate(ManagerPrincipal managerPrincipal, ProgramCreateReq programCreateReq) {
        Long facilityId = managerPrincipal.getFacilityId();
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        // S3 프로그램 기획서 업로드
        String proposalUrl = null;
        if (programCreateReq.getProposal() != null && !programCreateReq.getProposal().isEmpty()) {
            try {
                proposalUrl = s3Service.uploadFile(programCreateReq.getProposal());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // S3 사진 업로드
        List<String> imageUrls = new ArrayList<>();
        if (programCreateReq.getImages() != null && !programCreateReq.getImages().isEmpty()) {
            for (MultipartFile img : programCreateReq.getImages()) {
                if (img != null && !img.isEmpty()) {
                    try {
                        String url = s3Service.uploadFile(img);
                        imageUrls.add(url);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        Program program = Program.toEntity(programCreateReq, nursingFacility, proposalUrl, imageUrls);

        programRepository.save(program);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramListRes getPrograms(
            ManagerPrincipal managerPrincipal, ProgramCategory programCategory, Pageable pageable
    ) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        Page<Program> programPage;

        if (programCategory == null) {
            programPage = programRepository.findByNursingFacility(nursingFacility, pageable);
        } else {
            programPage = programRepository.findByNursingFacilityAndCategory(nursingFacility, programCategory, pageable);
        }

        Page<ProgramSummaryInfoRes> summaryPage = programPage.map(ProgramSummaryInfoRes::from);

        PageInfo pageInfo = PageInfo.from(summaryPage);

        return new ProgramListRes(
                summaryPage.getContent(),
                pageInfo
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramDetailInfoRes getProgram(ManagerPrincipal managerPrincipal, Long programId) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(nursingFacility.getId())) {
            throw new ProgramNotFoundException();
        }

        return ProgramDetailInfoRes.from(program);
    }

    @Override
    public ProgramDetailInfoRes modifyProgram(
            ManagerPrincipal managerPrincipal, Long programId, ProgramModifyReq programModifyReq
    ) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(nursingFacility.getId())) {
            throw new ProgramNotFoundException();
        }

        // 첨부파일 url
        String finalProposalUrl = null;
        if (programModifyReq.getIsDeleteProposal() != null && programModifyReq.getIsDeleteProposal()) {
            // 기존 파일이 있었다면 S3에서 삭제
            if (program.getProposalUrl() != null) {
                s3Service.deleteFile(program.getProposalUrl());
            }

            if (programModifyReq.getProposal() != null && !programModifyReq.getProposal().isEmpty()) {
                try {
                    finalProposalUrl = s3Service.uploadFile(programModifyReq.getProposal());
                } catch (IOException e) {
                    throw new RuntimeException("S3 파일 업로드 중 오류가 발생했습니다.", e);
                }
            }
        }

        // 파일 url
        List<String> oldUrlsInDb = new ArrayList<>(program.getImageUrls());
        List<String> urlsToKeep = (programModifyReq.getExistingImageUrls() != null)
                ? programModifyReq.getExistingImageUrls() : new ArrayList<>();
        List<String> finalImageUrls;
        try {
            finalImageUrls = updateImageFiles(
                    s3Service, oldUrlsInDb, urlsToKeep,
                    programModifyReq.getNewImages()
            );
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 중 오류가 발생했습니다.", e);
        }

        Program updatedProgram = program.updateEntity(programModifyReq, finalProposalUrl, finalImageUrls);

        return ProgramDetailInfoRes.from(updatedProgram);
    }

    @Override
    public void deleteProgram(ManagerPrincipal managerPrincipal, Long programId) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(nursingFacility.getId())) {
            throw new ProgramNotFoundException();
        }

        programRepository.delete(program);
    }
}
