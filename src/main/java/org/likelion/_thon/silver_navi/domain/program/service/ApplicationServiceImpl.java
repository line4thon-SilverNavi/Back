package org.likelion._thon.silver_navi.domain.program.service;

import lombok.RequiredArgsConstructor;

import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.ApplicationInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.ApplicationSummaryInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.PageInfo;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.likelion._thon.silver_navi.domain.program.utils.ApplicationSummaryProvider.getMonthlySummary;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ProgramApplyRepository programApplyRepository;
    private final ProgramRepository programRepository;

    @Override
    @Transactional(readOnly = true)
    public ApplicationManagementRes getApplications(
            ManagerPrincipal managerPrincipal, ApplicationStatus applicationStatus, Pageable pageable
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        List<Long> programIds = programRepository.findIdsByNursingFacilityId(facilityId);

        // 요약 정보
        ApplicationSummaryInfoRes applicationSummaryInfoRes = getMonthlySummary(programIds, programApplyRepository);

        // 신청 정보 리스트 및 페이지 정보
        Page<ProgramApply> applicationPage;
        if (applicationStatus == null) {
            applicationPage = programApplyRepository.findPageByProgramIds(programIds, pageable);
        } else {
            applicationPage = programApplyRepository.findPageByProgramIdsAndStatus(programIds, applicationStatus, pageable);
        }
        Page<ApplicationInfoRes> infoPage = applicationPage.map(ApplicationInfoRes::from);

        PageInfo pageInfo = PageInfo.from(infoPage);

        return new ApplicationManagementRes(
                applicationSummaryInfoRes,
                infoPage.getContent(),
                pageInfo
        );
    }
}
