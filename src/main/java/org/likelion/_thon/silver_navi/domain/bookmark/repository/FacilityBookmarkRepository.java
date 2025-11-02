package org.likelion._thon.silver_navi.domain.bookmark.repository;

import org.likelion._thon.silver_navi.domain.bookmark.entity.FacilityBookmark;
import org.likelion._thon.silver_navi.domain.bookmark.entity.ProgramBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityBookmarkRepository extends JpaRepository<FacilityBookmark, Long> {
    Optional<FacilityBookmark> findByUser_IdAndFacility_Id(Long userId, Long facilityId);
}
