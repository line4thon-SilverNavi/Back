package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NursingFacilityServiceImpl implements NursingFacilityService {

    private final NursingFacilityRepository nursingFacilityRepository;
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
    public NursingFacilityDetailInfoRes updateFacility(
            ManagerPrincipal managerPrincipal, NursingFacilityModifyReq req
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        Long managerId = managerPrincipal.getId();
        if (nursingFacility.getManager() == null || !nursingFacility.getManager().getId().equals(managerId)) {
            throw new FacilityAccessDeniedException();
        }

        // 파일 삭제할 URL 찾기
        List<String> oldUrlsInDb = new ArrayList<>(nursingFacility.getImageUris());
        List<String> urlsToKeep = (req.getExistingImageUrls() != null) ? req.getExistingImageUrls() : new ArrayList<>();
        List<String> urlsToDelete = oldUrlsInDb.stream()
                .filter(oldUrl -> !urlsToKeep.contains(oldUrl))
                .toList();

        // S3 파일 삭제
        if (!urlsToDelete.isEmpty()) {
            for (String url : urlsToDelete) {
                s3Service.deleteImage(url);
            }
        }
        // S3 파일 업로드
        List<String> newUploadedUrls = new ArrayList<>();
        if (req.getNewImages() != null && !req.getNewImages().isEmpty()) {
            for (MultipartFile img : req.getNewImages()) {
                if (img != null && !img.isEmpty()) {
                    try {
                        String newUrl = s3Service.uploadFile(img);
                        newUploadedUrls.add(newUrl);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        // 실제로 DB에 저장되었던 파일들인지 검증
        List<String> actualUrlsToKeep = oldUrlsInDb.stream()
                .filter(oldUrl -> urlsToKeep.contains(oldUrl))
                .toList();
        List<String> finalImageUrls = new ArrayList<>(actualUrlsToKeep);
        // 새로 올라온 파일들 추가
        finalImageUrls.addAll(newUploadedUrls);

        nursingFacility.updateDetails(req, finalImageUrls);

        return NursingFacilityDetailInfoRes.from(nursingFacility);
    }

    @Override
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

        // 각 시설별 리뷰 통계 및 거리 계산
        return facilities.stream()
                .map(facility -> {
                    double distanceKm = GeoUtils.calculateDistance(
                            lat, lng,
                            facility.getLatitude(), facility.getLongitude()
                    );

                    return NearbyFacilityRes.of(
                            facility,
                            distanceKm,
                            facility.getAverageRating(),
                            facility.getReviewCount()
                    );
                })
                .sorted(Comparator.comparingDouble(NearbyFacilityRes::distanceKm))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserByFacilityInfoRes facilityDetail(Long facilityId, User user) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);
        return UserByFacilityInfoRes.from(nursingFacility,user.getLatitude(),user.getLongitude());
    }
}
