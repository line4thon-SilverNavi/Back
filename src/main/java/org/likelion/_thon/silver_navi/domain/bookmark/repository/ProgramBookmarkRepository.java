package org.likelion._thon.silver_navi.domain.bookmark.repository;

import org.likelion._thon.silver_navi.domain.bookmark.entity.ProgramBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramBookmarkRepository extends JpaRepository<ProgramBookmark, Long> {
    Optional<ProgramBookmark> findByUser_IdAndProgram_Id(Long userId, Long programId);

    boolean existsByUser_IdAndProgram_Id(Long userId, Long programId);
}
