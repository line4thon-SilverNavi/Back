package org.likelion._thon.silver_navi.domain.program.service;

import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface ProgramApplyService {
    void applyProgram(User user, Long programId, ProgramApplyReq req);
}
