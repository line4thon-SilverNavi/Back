package org.likelion._thon.silver_navi.domain.consult.service;

import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface ConsultService {
    void applyGeneral(User user, GeneralApplyReq req);

    void apply(User user, ConsultApplyReq req);
}
