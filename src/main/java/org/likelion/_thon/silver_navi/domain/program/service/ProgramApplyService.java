package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.web.dto.AttendanceUpdateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramApplicationInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;

public interface ProgramApplyService {
    void applyProgram(User user, Long programId, ProgramApplyReq req);

    // 프로그램 신청자들 확인
    ProgramApplicationInfoRes getProgramApplicants(ManagerPrincipal managerPrincipal, Long programId);

    // 프로그램 신청자들 출결 업데이트
    void updateAttendanceStatus(
            ManagerPrincipal managerPrincipal, Long programId, AttendanceUpdateReq attendanceUpdateReq);
}
