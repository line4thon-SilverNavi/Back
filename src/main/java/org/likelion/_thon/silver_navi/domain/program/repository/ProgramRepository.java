package org.likelion._thon.silver_navi.domain.program.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Page<Program> findByNursingFacility(NursingFacility nursingFacility, Pageable pageable);
    Page<Program> findByNursingFacilityAndCategory(
            NursingFacility nursingFacility, ProgramCategory category, Pageable pageable
    );

    @Query("SELECT p.id FROM Program p WHERE p.nursingFacility.id = :facilityId")
    List<Long> findIdsByNursingFacilityId(@Param("facilityId") Long facilityId);
}
