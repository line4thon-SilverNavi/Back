package org.likelion._thon.silver_navi.domain.nursingfacility.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NursingFacilityRepository extends JpaRepository<NursingFacility, Long> {

    Optional<NursingFacility> findByName(String name);
}
