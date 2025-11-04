package org.likelion._thon.silver_navi.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.entity.FacilityBookmark;
import org.likelion._thon.silver_navi.domain.bookmark.repository.FacilityBookmarkRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Override
    public List<NearbyFacilityRes> getBookmarks(User user) {
        return facilityBookmarkRepository.findAllByUser(user).stream()
                .map(bookmark -> {
                    NursingFacility facility = bookmark.getFacility();
                    double distanceKm = GeoUtils.calculateDistance(
                            user.getLatitude(),
                            user.getLongitude(),
                            facility.getLatitude(),
                            facility.getLongitude()
                    );
                    return NearbyFacilityRes.of(facility, distanceKm, true);
                })
                .toList();
    }
}
