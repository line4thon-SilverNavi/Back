package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityAccessDeniedException;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDeatailsInfoReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NursingFacilityServiceImpl implements NursingFacilityService {

    private final NursingFacilityRepository nursingFacilityRepository;
    private final S3Service s3Service;

    @Override
    @Transactional(readOnly = true)
    public NursingFacilityDetailInfoRes getFacility(Long managerId, Long facilityId) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        if (!nursingFacility.getManager().getId().equals(managerId)) {
            throw new FacilityNotFoundException();
        }

        return new NursingFacilityDetailInfoRes(
                nursingFacility.getName(),
                nursingFacility.getCategory() != null ? nursingFacility.getCategory().getValue() : null,
                nursingFacility.getOperatingHours(),
                nursingFacility.getFacilityNumber(),
                nursingFacility.getAddress(),
                nursingFacility.getDescription(),
                nursingFacility.getServices(),
                nursingFacility.getImageUris()
        );
    }

    @Override
    @Transactional
    public NursingFacilityDetailInfoRes updateFacility(
            ManagerPrincipal managerPrincipal, NursingFacilityDeatailsInfoReq req
    ) {
        Long facilityId = managerPrincipal.getFacilityId();
        NursingFacility facility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        Long managerId = managerPrincipal.getId();
        if (facility.getManager() == null || !facility.getManager().getId().equals(managerId)) {
            throw new FacilityAccessDeniedException();
        }

        // 파일 삭제할 URL 찾기
        List<String> oldUrlsInDb = new ArrayList<>(facility.getImageUris());
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
                        String newUrl = s3Service.uploadImage(img);
                        newUploadedUrls.add(newUrl);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        List<String> finalImageUrls = new ArrayList<>(urlsToKeep);
        finalImageUrls.addAll(newUploadedUrls);

        facility.updateDetails(req, finalImageUrls);

        return new NursingFacilityDetailInfoRes(
                facility.getName(),
                facility.getCategory() != null ? facility.getCategory().getValue() : null,
                facility.getOperatingHours(),
                facility.getFacilityNumber(),
                facility.getAddress(),
                facility.getDescription(),
                facility.getServices(),
                facility.getImageUris()
        );
    }
}
