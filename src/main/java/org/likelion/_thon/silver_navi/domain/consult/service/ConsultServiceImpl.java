package org.likelion._thon.silver_navi.domain.consult.service;

import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.consult.repository.ConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.repository.GeneralConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;

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
}
