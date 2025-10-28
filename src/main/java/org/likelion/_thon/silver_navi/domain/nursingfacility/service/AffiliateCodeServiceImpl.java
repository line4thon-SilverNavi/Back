package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.AffiliateCode;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode.AffiliateCodeAlreadyIssuedException;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.AffiliateCodeRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.AffiliateCodeCreateRes;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AffiliateCodeServiceImpl implements AffiliateCodeService {

    private final AffiliateCodeRepository partnershipCodeRepository;
    private final NursingFacilityRepository nursingFacilityRepository;
    private final TextEncryptor textEncryptor;

    @Override
    @Transactional
    public AffiliateCodeCreateRes createPartnershipCode(Long facilityId) {
        NursingFacility facility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        if (partnershipCodeRepository.existsByNursingFacilityId(facilityId)) {
            throw new AffiliateCodeAlreadyIssuedException();
        }

        // 제휴 코드 생성
        String plainTextCode = null;
        do {
            plainTextCode = generateRandomCode();
        } while (partnershipCodeRepository.existsByCode(textEncryptor.encrypt(plainTextCode)));

        // 코드 암호화 (양방향 암호화)
        String encryptedCode = textEncryptor.encrypt(plainTextCode);

        AffiliateCode newCode = AffiliateCode.toEntity(encryptedCode, facility);
        partnershipCodeRepository.save(newCode);

        return new AffiliateCodeCreateRes(
                newCode.getNursingFacility().getId(),
                plainTextCode,
                newCode.getCreatedAt()
        );
    }

    // 8자리의 랜덤 코드 생성 (예: A4B1-C9D2)
    private String generateRandomCode() {
        String uuid = UUID.randomUUID().toString().toUpperCase();
        return uuid.substring(0, 4) + "-" + uuid.substring(9, 13);
    }
}
