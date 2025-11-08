package org.likelion._thon.silver_navi.domain.consult.service;

import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultHistorySummaryRes;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultManagementRes;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ConsultService {
    void applyGeneral(User user, GeneralApplyReq req);

    void apply(User user, ConsultApplyReq req);

    ConsultHistorySummaryRes getConsultHistory(User user);

    // 상담 요약 정보 및 상담 리스트 조회
    ConsultManagementRes getConsultManagement(
            ManagerPrincipal managerPrincipal, ConsultStatus status, Pageable pageable
    );
}
