package org.likelion._thon.silver_navi.domain.consult.service;

import jakarta.validation.Valid;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface GeneralConsultService {
    void apply(User user, GeneralApplyReq req);
}
