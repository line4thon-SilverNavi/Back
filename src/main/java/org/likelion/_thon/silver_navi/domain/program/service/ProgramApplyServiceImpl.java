package org.likelion._thon.silver_navi.domain.program.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.exception.InvalidCareTargetException;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramAlreadyAppliedException;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramNotFoundException;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramApplyReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramApplyServiceImpl implements ProgramApplyService {
    private final ProgramApplyRepository programApplyRepository;
    private final ProgramRepository programRepository;

    @Override
    @Transactional
    public void applyProgram(User user, Long programId, ProgramApplyReq req) {
        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        //중복 신청 확인
        if (programApplyRepository.existsByUserAndProgram(user, program)) {
            throw new ProgramAlreadyAppliedException();
        }

        //돌봄 대상자 정보 확인
        CareTarget careTarget = Optional.ofNullable(user.getCareTarget())
                .orElseThrow(InvalidCareTargetException::new);

        if (careTarget.getBirthDate() == null ||
                careTarget.getGender() == null ||
                careTarget.getCareGrade() == null) {
            throw new InvalidCareTargetException();
        }

        ProgramApply apply = ProgramApply.create(user, program, req.getContent());
        programApplyRepository.save(apply);
    }
}
