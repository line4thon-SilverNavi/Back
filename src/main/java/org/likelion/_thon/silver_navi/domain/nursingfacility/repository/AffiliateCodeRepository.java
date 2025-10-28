package org.likelion._thon.silver_navi.domain.nursingfacility.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.AffiliateCode;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliateCodeRepository extends JpaRepository<AffiliateCode, Long> {

    boolean existsByNursingFacilityId(Long facilityId); // facilityId로 코드가 이미 발급되었는지 확인
    boolean existsByCode(String code); // 생성된 코드가 중복되는지 확인

    Optional<AffiliateCode> findByNursingFacility(NursingFacility facility);
}
