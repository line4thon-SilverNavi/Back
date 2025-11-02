package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ProgramService {

    void programCreate(ManagerPrincipal managerPrincipal, ProgramCreateReq programCreateReq);
    // 전체 조회
    ProgramListRes getPrograms(ManagerPrincipal managerPrincipal, ProgramCategory programCategory, Pageable pageable);
    // 단일 조회
    ProgramDetailInfoRes getProgram(ManagerPrincipal managerPrincipal, Long programId);
    // 프로그램 수정
    ProgramDetailInfoRes modifyProgram(ManagerPrincipal managerPrincipal, Long programId, ProgramModifyReq programModifyReq);
    // 프로그램 삭제
    void deleteProgram(ManagerPrincipal managerPrincipal, Long programId);

    UserByProgramInfoRes programDetails(User user, Long programId);
}
