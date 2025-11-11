package org.likelion._thon.silver_navi.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.likelion._thon.silver_navi.domain.manager.exception.ManagerLoginIdDuplicateException;
import org.likelion._thon.silver_navi.domain.manager.exception.ManagerNotFoundException;
import org.likelion._thon.silver_navi.domain.manager.exception.ManagerPasswordInvalidException;
import org.likelion._thon.silver_navi.domain.manager.repository.ManagerRepository;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignInRes;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignUpReq;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSingInReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.AffiliateCode;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode.AffiliateCodeMisMatchException;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode.AffiliateCodeNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.AffiliateCodeRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInRes;
import org.likelion._thon.silver_navi.global.auth.jwt.JwtTokenProvider;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final NursingFacilityRepository nursingFacilityRepository;
    private final AffiliateCodeRepository affiliateCodeRepository;
    private final ManagerRepository managerRepository;

    private final TextEncryptor textEncryptor;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void checkLoginId(String loginId) {
        if (managerRepository.existsByLoginId(loginId)) { throw new ManagerLoginIdDuplicateException(); }
    }

    @Override
    @Transactional
    public void signUp(ManagerSignUpReq managerSignUpReq) {
        NursingFacility nursingFacility = nursingFacilityRepository.findByName(managerSignUpReq.getName())
                .orElseThrow(FacilityNotFoundException::new);

        AffiliateCode affiliateCode = affiliateCodeRepository.findByNursingFacility(nursingFacility)
                .orElseThrow(AffiliateCodeNotFoundException::new);

        if (!textEncryptor.decrypt(affiliateCode.getCode()).equals(managerSignUpReq.getAffiliateCode())) {
            throw new AffiliateCodeMisMatchException();
        }

        String loginId = managerSignUpReq.getLoginId();
        checkLoginId(loginId);

        String encoded = passwordEncoder.encode(managerSignUpReq.getPassword());
        Manager manager = Manager.toEntity(loginId, encoded, nursingFacility);

        managerRepository.save(manager);

        affiliateCode.codeUsed();
        nursingFacility.update();
    }

    @Override
    public ManagerSignInRes signin(ManagerSingInReq managerSingInReq) {
        String loginid = managerSingInReq.getLoginId();
        Manager manager = managerRepository.findByLoginId(loginid)
                .orElseThrow(ManagerNotFoundException::new);

        String password = managerSingInReq.getPassword();
        if (!passwordEncoder.matches(password, manager.getPassword())) {
            throw new ManagerPasswordInvalidException();
        }

        String token = jwtTokenProvider.createToken(manager);

        return new ManagerSignInRes(manager.getNursingFacility().getName(), token);
    }
}
