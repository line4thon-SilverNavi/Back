package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.repository.FacilityBookmarkRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityAccessDeniedException;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityModifyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.UserByFacilityInfoRes;
import org.likelion._thon.silver_navi.domain.review.repository.ReviewRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.likelion._thon.silver_navi.global.util.geo.BoundingBox;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.likelion._thon.silver_navi.global.util.s3.UpdateImagesUtils.updateImageFiles;

@Service
@RequiredArgsConstructor
public class NursingFacilityServiceImpl implements NursingFacilityService {

    private final NursingFacilityRepository nursingFacilityRepository;
    private final FacilityBookmarkRepository facilityBookmarkRepository;
    private final ReviewRepository reviewRepository;
    private final S3Service s3Service;

    @Override
    @Transactional(readOnly = true)
    public NursingFacilityDetailInfoRes getFacility(Long managerId, Long facilityId) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        if (!nursingFacility.getManager().getId().equals(managerId)) {
            throw new FacilityNotFoundException();
        }

        return NursingFacilityDetailInfoRes.from(nursingFacility);
    }

    @Override
    @Transactional
    public NursingFacilityDetailInfoRes modifyFacility(
            ManagerPrincipal managerPrincipal, NursingFacilityModifyReq nursingFacilityModifyReq
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        Long managerId = managerPrincipal.getId();
        if (nursingFacility.getManager() == null || !nursingFacility.getManager().getId().equals(managerId)) {
            throw new FacilityAccessDeniedException();
        }

        // 파일 url
        List<String> finalImageUrls = updateImageFiles(
                s3Service,
                nursingFacility.getImageUrls(),
                nursingFacilityModifyReq.getExistingImageUrls(),
                nursingFacilityModifyReq.getNewImages()
        );

        NursingFacility updatedNursingFacility = nursingFacility.updateEntity(nursingFacilityModifyReq, finalImageUrls);

        return NursingFacilityDetailInfoRes.from(updatedNursingFacility);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NearbyFacilityRes> findNearbyFacilities(User user) {
        double lat = user.getLatitude();
        double lng = user.getLongitude();
        double radius = user.getSearchRadius(); // km 단위

        // Bounding Box 계산
        BoundingBox bbox = GeoUtils.calculateBoundingBox(lat, lng, radius);

        // 반경 내 시설 조회
        List<NursingFacility> facilities = nursingFacilityRepository.findNearbyFacilities(
                lat, lng, radius,
                bbox.minLat(), bbox.maxLat(),
                bbox.minLng(), bbox.maxLng()
        );

        // 사용자 북마크된 시설 목록 미리 조회
        Set<Long> bookmarkedFacilityIds = facilityBookmarkRepository.findAllByUser(user)
                .stream()
                .map(bookmark -> bookmark.getFacility().getId())
                .collect(Collectors.toSet());

        // 각 시설별 거리 + 북마크 여부 매핑
        return facilities.stream()
                .map(facility -> {
                    double distanceKm = GeoUtils.calculateDistance(
                            lat, lng,
                            facility.getLatitude(), facility.getLongitude()
                    );

                    boolean bookmarked = bookmarkedFacilityIds.contains(facility.getId());
                    return NearbyFacilityRes.of(facility, distanceKm, bookmarked);
                })
                .sorted(Comparator.comparingDouble(NearbyFacilityRes::distanceKm))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserByFacilityInfoRes facilityDetail(Long facilityId, User user) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        boolean bookmarked = facilityBookmarkRepository.existsByUser_IdAndFacility_Id(user.getId(),nursingFacility.getId());

        return UserByFacilityInfoRes.from(nursingFacility,user.getLatitude(),user.getLongitude(),bookmarked);
    }
}
