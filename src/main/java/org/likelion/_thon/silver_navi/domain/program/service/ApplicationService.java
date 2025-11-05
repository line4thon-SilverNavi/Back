package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationStatusUpdateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationSummaryRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationUserInfoRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {
    // 신청 목록 보기
    ApplicationManagementRes getApplications(
            ManagerPrincipal managerPrincipal, ApplicationStatus applicationStatus, Pageable pageable);
    // 신청자 정보 보기
    ApplicationUserInfoRes getApplicationInfo(
            ManagerPrincipal managerPrincipal, Long applicationId
    );
    // 신청자 상태 업데이트
    void updateApplicationStatus(
            ManagerPrincipal managerPrincipal, Long applicationId, ApplicationStatusUpdateReq applicationStatusUpdateReq);

    ApplicationSummaryRes getUserByApplications(User user);
}
