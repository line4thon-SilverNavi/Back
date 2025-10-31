package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes.PageInfo;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramSummaryInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
}
