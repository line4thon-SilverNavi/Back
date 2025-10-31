package org.likelion._thon.silver_navi.domain.program.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Page<Program> findByNursingFacility(NursingFacility nursingFacility, Pageable pageable);
    Page<Program> findByNursingFacilityAndCategory(
            NursingFacility nursingFacility, ProgramCategory category, Pageable pageable
    );
}
