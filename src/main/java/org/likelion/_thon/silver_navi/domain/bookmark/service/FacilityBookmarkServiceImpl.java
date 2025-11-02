package org.likelion._thon.silver_navi.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.entity.FacilityBookmark;
import org.likelion._thon.silver_navi.domain.bookmark.repository.FacilityBookmarkRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacilityBookmarkServiceImpl implements FacilityBookmarkService {
    private final FacilityBookmarkRepository facilityBookmarkRepository;
    private final NursingFacilityRepository nursingFacilityRepository;

    @Override
    @Transactional
    public boolean toggle(User user, Long contentId) {
        Optional<FacilityBookmark> foundBookmark =
                facilityBookmarkRepository.findByUser_IdAndFacility_Id(user.getId(), contentId);

        // 좋아요가 눌려있는 경우
        if (foundBookmark.isPresent()) {
            facilityBookmarkRepository.delete(foundBookmark.get());
            return false; // 북마크 OFF
        }

        var facility = nursingFacilityRepository.findById(contentId)
                .orElseThrow(FacilityNotFoundException::new);

        FacilityBookmark bookmark = FacilityBookmark.create(user, facility);

        facilityBookmarkRepository.save(bookmark);
        return true; // 북마크 ON
    }
}
