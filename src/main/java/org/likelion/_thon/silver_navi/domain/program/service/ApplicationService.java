package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    ApplicationManagementRes getApplications(
            ManagerPrincipal managerPrincipal, ApplicationStatus applicationStatus, Pageable pageable);
}
