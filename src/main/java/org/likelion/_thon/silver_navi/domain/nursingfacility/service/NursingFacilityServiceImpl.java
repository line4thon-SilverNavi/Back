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

        return NursingFacilityDetailInfoRes.from(nursingFacility);
    }

    @Override
    @Transactional
    public NursingFacilityDetailInfoRes updateFacility(
            ManagerPrincipal managerPrincipal, NursingFacilityDeatailsInfoReq req
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
}
