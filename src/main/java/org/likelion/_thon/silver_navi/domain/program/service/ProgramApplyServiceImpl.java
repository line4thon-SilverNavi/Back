package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.exception.InvalidCareTargetException;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.exception.*;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public ProgramApplicationInfoRes getProgramApplicants(ManagerPrincipal managerPrincipal, Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ProgramAccessDeniedException();
        }

        // 승인된 사람들만
        List<ProgramApply> applies = program.getApplies().stream()
                .filter(apply -> ApplicationStatus.APPROVED.equals(apply.getStatus()))
                .toList();

        ProgramApplicationSummaryRes summary = ProgramApplicationSummaryRes.from(applies);

        List<ProgramApplicantsRes> applicantList = applies.stream()
                .map(ProgramApplicantsRes::from)
                .toList();

        return new ProgramApplicationInfoRes(
                summary,
                applicantList
        );
    }

    @Override
    @Transactional
    public void updateAttendanceStatus(
            ManagerPrincipal managerPrincipal, Long programId, AttendanceUpdateReq attendanceUpdateReq
    ) {
        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        if (!program.getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ProgramAccessDeniedException();
        }

        List<ProgramApply> applies = programApplyRepository.findByIdIn(attendanceUpdateReq.getApplicantIds())
                .stream()
                .filter(apply -> ApplicationStatus.APPROVED.equals(apply.getStatus()))
                .toList();
        if (applies.size() != attendanceUpdateReq.getApplicantIds().size()) {
            throw new ProgramApplicantNotFoundException();
        }

        for (ProgramApply apply : applies) {
            if (!apply.getProgram().getId().equals(programId)) {
                throw new ProgramApplicantInvalidException();
            }
        }

        applies.forEach(ProgramApply::updateAttendanceStatus);
    }
}
