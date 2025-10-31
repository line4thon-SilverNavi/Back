package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ProgramService {

    void programCreate(ManagerPrincipal managerPrincipal, ProgramCreateReq programCreateReq);

    ProgramListRes getPrograms(ManagerPrincipal managerPrincipal, Pageable pageable);
}
