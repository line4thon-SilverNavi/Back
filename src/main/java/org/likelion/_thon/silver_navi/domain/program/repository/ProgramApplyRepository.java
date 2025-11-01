package org.likelion._thon.silver_navi.domain.program.repository;

import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramApplyRepository extends JpaRepository<ProgramApply, Long> {
    boolean existsByUserAndProgram(User user, Program program);
}
