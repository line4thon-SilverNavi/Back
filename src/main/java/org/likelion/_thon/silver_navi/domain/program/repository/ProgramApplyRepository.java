package org.likelion._thon.silver_navi.domain.program.repository;

import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgramApplyRepository extends JpaRepository<ProgramApply, Long> {
    boolean existsByUserAndProgram(User user, Program program);

    // 이번 달 상태별 신청 건수
    long countByProgramIdInAndStatusAndCreatedAtBetween(
            List<Long> programIds, ApplicationStatus status, LocalDateTime start, LocalDateTime end);

    // 상태 무관, 페이징 조회 (N+1 방지)
    @Query(value = "SELECT pa FROM ProgramApply pa " +
            "JOIN FETCH pa.program p " +
            "JOIN FETCH pa.user u " +
            "WHERE pa.program.id IN :programIds",
            countQuery = "SELECT COUNT(pa) FROM ProgramApply pa " +
                    "WHERE pa.program.id IN :programIds")
    Page<ProgramApply> findPageByProgramIds(
            @Param("programIds") List<Long> programIds, Pageable pageable);

    // 상태 필터, 페이징 조회 (N+1 방지)
    @Query(value = "SELECT pa FROM ProgramApply pa " +
            "JOIN FETCH pa.program p " +
            "JOIN FETCH pa.user u " +
            "WHERE pa.program.id IN :programIds AND pa.status = :status",
            countQuery = "SELECT COUNT(pa) FROM ProgramApply pa " +
                    "WHERE pa.program.id IN :programIds AND pa.status = :status")
    Page<ProgramApply> findPageByProgramIdsAndStatus(
            @Param("programIds") List<Long> programIds,
            @Param("status") ApplicationStatus status,
            Pageable pageable);

    // 출결 상태 변경 신청자들 찾기
    List<ProgramApply> findByIdIn(List<Long> ids);
}
