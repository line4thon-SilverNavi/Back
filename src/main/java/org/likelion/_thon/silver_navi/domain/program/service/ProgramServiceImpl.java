package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion._thon.silver_navi.domain.bookmark.repository.ProgramBookmarkRepository;
import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.repository.NotificationRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramAccessDeniedException;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramNotFoundException;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes.PageInfo;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.likelion._thon.silver_navi.global.util.geo.BoundingBox;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.likelion._thon.silver_navi.global.util.s3.UpdateImagesUtils.updateImageFiles;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final NursingFacilityRepository nursingFacilityRepository;
    private final ProgramBookmarkRepository programBookmarkRepository;
    private final ProgramApplyRepository programApplyRepository;
    private final NotificationRepository notificationRepository;

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
            throw new ProgramAccessDeniedException();
        }

        return ProgramDetailInfoRes.from(program);
    }

    @Override
    @Transactional
    public ProgramDetailInfoRes modifyProgram(
            ManagerPrincipal managerPrincipal, Long programId, ProgramModifyReq programModifyReq
    ) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(nursingFacility.getId())) {
            throw new ProgramAccessDeniedException();
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
        List<String> finalImageUrls = updateImageFiles(
                s3Service,
                program.getImageUrls(),
                programModifyReq.getExistingImageUrls(),
                programModifyReq.getNewImages()
        );

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
            throw new ProgramAccessDeniedException();
        }

        programRepository.delete(program);
    }

    @Override
    public UserByProgramInfoRes programDetails(User user, Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);
        boolean bookmarked = programBookmarkRepository.existsByUser_IdAndProgram_Id(user.getId(), program.getId());

        return UserByProgramInfoRes.from(program, bookmarked);
    }

    @Override
    public List<UserByProgramListRes> findPrograms(User user) {
        double userLat = user.getLatitude();
        double userLng = user.getLongitude();
        double radius = user.getSearchRadius(); // km 단위

        // Bounding Box 계산
        BoundingBox bbox = GeoUtils.calculateBoundingBox(userLat, userLng, radius);

        // 반경 내 시설에 속한 프로그램 조회
        List<Program> programs = programRepository.findNearbyPrograms(
                userLat, userLng, radius,
                bbox.minLat(), bbox.maxLat(),
                bbox.minLng(), bbox.maxLng()
        );

        // 사용자 북마크 조회
        Set<Long> bookmarkedProgramIds = programBookmarkRepository.findAllByUser(user)
                .stream()
                .map(bookmark -> bookmark.getProgram().getId())
                .collect(Collectors.toSet());

        return programs.stream()
                .map(program -> {
                    boolean bookmarked = bookmarkedProgramIds.contains(program.getId());
                    String thumbnail = program.getImageUrls().isEmpty()
                            ? null
                            : program.getImageUrls().getFirst();
                    return UserByProgramListRes.from(program, thumbnail, bookmarked);
                })
                .toList();
    }

    @Scheduled(cron = "0 0 5 * * *")  // 초 분 시 일 월 요일 (매일 05:00)
    @Transactional
    public void createProgramReminders() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Program> programs = programRepository.findAllByDateIn(List.of(today, tomorrow));


        if (programs.isEmpty()) {
            log.info("[Program Reminder] 오늘 및 내일 진행 예정인 프로그램 없습니다.");
            return;
        }

        for (Program program : programs) {
            List<ProgramApply> applies = programApplyRepository.findAllByProgram(program);

            for (ProgramApply apply : applies) {
                User user = apply.getUser();
                Notification notification = Notification.createProgramReminder(user, program.getId());
                notificationRepository.save(notification);
            }
        }

        log.info("[Program Reminder] 오늘 및 내일 총 {}개의 프로그램에 대한 알림 생성 완료.", programs.size());
    }
}
