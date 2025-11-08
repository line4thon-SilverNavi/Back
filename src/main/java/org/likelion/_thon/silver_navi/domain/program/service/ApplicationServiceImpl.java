package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;

import org.likelion._thon.silver_navi.domain.bookmark.repository.ProgramBookmarkRepository;
import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.repository.NotificationRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.exception.ApplicationAccessDeniedException;
import org.likelion._thon.silver_navi.domain.program.exception.ApplicationAlreadyProcessedException;
import org.likelion._thon.silver_navi.domain.program.exception.ApplicationNotFoundException;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.ApplicationInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.ApplicationSummaryInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.PageInfo;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationStatusUpdateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationUserInfoRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.likelion._thon.silver_navi.domain.program.utils.ApplicationSummaryProvider.getMonthlySummary;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ProgramBookmarkRepository programBookmarkRepository;
    private final ProgramApplyRepository programApplyRepository;
    private final ProgramRepository programRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public ApplicationManagementRes getApplications(
            ManagerPrincipal managerPrincipal, ApplicationStatus applicationStatus, Pageable pageable
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        List<Long> programIds = programRepository.findIdsByNursingFacilityId(facilityId);

        // 요약 정보
        ApplicationSummaryInfoRes applicationSummaryInfoRes = getMonthlySummary(programIds, programApplyRepository);

        // 신청 정보 리스트 및 페이지 정보
        Page<ProgramApply> applicationPage;
        if (applicationStatus == null) {
            applicationPage = programApplyRepository.findPageByProgramIds(programIds, pageable);
        } else {
            applicationPage = programApplyRepository.findPageByProgramIdsAndStatus(programIds, applicationStatus, pageable);
        }
        Page<ApplicationInfoRes> infoPage = applicationPage.map(ApplicationInfoRes::from);

        PageInfo pageInfo = PageInfo.from(infoPage);

        return new ApplicationManagementRes(
                applicationSummaryInfoRes,
                infoPage.getContent(),
                pageInfo
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationUserInfoRes getApplicationInfo(ManagerPrincipal managerPrincipal, Long applicationId) {
        ProgramApply programApply = programApplyRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        return ApplicationUserInfoRes.from(programApply);
    }

    @Override
    @Transactional
    public void updateApplicationStatus(
            ManagerPrincipal managerPrincipal, Long applicationId, ApplicationStatusUpdateReq applicationStatusUpdateReq
    ) {
        ProgramApply programApply = programApplyRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        if (!programApply.getProgram().getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ApplicationAccessDeniedException();
        }

        if (!programApply.getStatus().equals(ApplicationStatus.PENDING)) {
            throw new ApplicationAlreadyProcessedException();
        }

        programApply.updateStatus(applicationStatusUpdateReq.getIsApproved(), applicationStatusUpdateReq.getReason());

        // 알림 생성
        Notification notification = Notification.createProgramStatusChanged(
                programApply.getUser(),
                programApply.getId(),
                applicationStatusUpdateReq.getIsApproved()
        );
        notificationRepository.save(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationSummaryRes getUserByApplications(User user) {
        // 사용자가 신청한 프로그램 목록 조회
        List<ProgramApply> applies = programApplyRepository.findByUser(user);

        // 각 ProgramApply에서 Program 꺼내오기
        List<Program> programs = applies.stream()
                .map(ProgramApply::getProgram)
                .toList();

        // 북마크 정보 미리 조회
        Set<Long> bookmarkedIds = programBookmarkRepository.findAllByUser(user).stream()
                .map(pb -> pb.getProgram().getId())
                .collect(Collectors.toSet());

        List<UserProgramStatusRes> statusList = programs.stream()
                .map(program -> {
                    boolean bookmarked = bookmarkedIds.contains(program.getId());
                    String thumbnail = program.getImageUrls().isEmpty()
                            ? null
                            : program.getImageUrls().getFirst();
                    UserByProgramListRes base = UserByProgramListRes.from(program, thumbnail, bookmarked);
                    return UserProgramStatusRes.of(base, program);
                })
                .toList();

        return ApplicationSummaryRes.of(statusList);
    }
}
