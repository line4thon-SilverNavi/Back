package org.likelion._thon.silver_navi.domain.manager.service;

import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignInRes;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignUpReq;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSingInReq;

public interface ManagerService {

    void checkLoginId(String loginId);
    void signUp(ManagerSignUpReq managerSignUpReq);
    ManagerSignInRes signin(ManagerSingInReq managerSingInReq);
}
