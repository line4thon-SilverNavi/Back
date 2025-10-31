package org.likelion._thon.silver_navi.domain.program.repository;

import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
