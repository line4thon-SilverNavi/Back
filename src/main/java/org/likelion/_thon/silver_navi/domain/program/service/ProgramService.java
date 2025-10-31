package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;

public interface ProgramService {

    void programCreate(ManagerPrincipal managerPrincipal, ProgramCreateReq programCreateReq);
}
