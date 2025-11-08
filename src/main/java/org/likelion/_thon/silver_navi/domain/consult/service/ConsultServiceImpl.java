package org.likelion._thon.silver_navi.domain.consult.service;

import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultAccessDeniedException;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultNotFoundException;
import org.likelion._thon.silver_navi.domain.consult.repository.CombinedConsultDto;
import org.likelion._thon.silver_navi.domain.consult.repository.ConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.repository.GeneralConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.web.dto.*;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultManagementRes.ConsultSummaryInfoRes;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultManagementRes.ConsultInfoRes;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultManagementRes.PageInfo;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultServiceImpl implements ConsultService {
    private final NursingFacilityRepository nursingFacilityRepository;
    private final GeneralConsultRepository generalConsultRepository;
    private final ConsultRepository consultRepository;

    @Override
    @Transactional
    public void applyGeneral(User user, GeneralApplyReq req) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(req.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);
        GeneralConsult consult = GeneralConsult.toEntity(req, user, nursingFacility);
        generalConsultRepository.save(consult);
    }

    @Override
    @Transactional
    public void apply(User user, ConsultApplyReq req) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(req.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);
        Consult consult = Consult.toEntity(req, user, nursingFacility);
        consultRepository.save(consult);
    }

    @Override
    public ConsultHistorySummaryRes getConsultHistory(User user) {
        List<Consult> gradeConsults = consultRepository.findAllByUser(user);
        List<GeneralConsult> generalConsults = generalConsultRepository.findAllByUser(user);

        List<ConsultHistoryRes> combined = Stream.concat(
                        gradeConsults.stream().map(ConsultHistoryRes::from),
                        generalConsults.stream().map(ConsultHistoryRes::fromGeneral)
                ).sorted(Comparator.comparing(ConsultHistoryRes::createdAt).reversed())
                .toList();

        long total = combined.size();
        long waiting = combined.stream()
                .filter(c -> c.consultStatus().equals(ConsultStatus.WAITING.getValue()))
                .count();
        long confirmed = combined.stream()
                .filter(c -> c.consultStatus().equals(ConsultStatus.CONFIRMED.getValue()))
                .count();
        long completed = combined.stream()
                .filter(c -> c.consultStatus().equals(ConsultStatus.COMPLETED.getValue()))
                .count();
        return new ConsultHistorySummaryRes(total, waiting, confirmed, completed, combined);
    }

    // ---------------------------------------- 시설관리자 ----------------------------------------

    @Override
    public ConsultManagementRes getConsultManagement(
            ManagerPrincipal managerPrincipal, ConsultStatus status, Pageable pageable
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        Page<CombinedConsultDto> consultPage = consultRepository.findCombinedConsultByFacilityId(
                facilityId, status, pageable);
        Page<ConsultInfoRes> consultInfoPage = consultPage.map(ConsultInfoRes::from);

        ConsultSummaryInfoRes summary = ConsultSummaryInfoRes.of(
                facilityId, generalConsultRepository, consultRepository, consultPage
        );

        PageInfo pageInfo = PageInfo.from(consultInfoPage);

        return new ConsultManagementRes(
                summary,
                consultInfoPage.getContent(),
                pageInfo
        );
    }

    @Override
    public ConsultDetailInfoRes getConsult(
            ManagerPrincipal managerPrincipal, Long consultId, ConsultCategory consultCategory
    ) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        ConsultDetailInfoRes consultDetailInfoRes;
        if (consultCategory.equals(ConsultCategory.GRADE)) {    // 상담
            Consult consult = consultRepository.findById(consultId)
                    .orElseThrow(ConsultNotFoundException::new);

            if (!consult.getFacility().getId().equals(nursingFacility.getId())) {
                throw new ConsultAccessDeniedException();
            }

            consultDetailInfoRes = ConsultDetailInfoRes.from(consult);
        } else {    // 일반 상담
            GeneralConsult consult = generalConsultRepository.findById(consultId)
                    .orElseThrow(ConsultNotFoundException::new);

            if (!consult.getFacility().getId().equals(nursingFacility.getId())) {
                throw new ConsultAccessDeniedException();
            }

            consultDetailInfoRes = ConsultDetailInfoRes.from(consult);
        }

        return consultDetailInfoRes;
    }
}
