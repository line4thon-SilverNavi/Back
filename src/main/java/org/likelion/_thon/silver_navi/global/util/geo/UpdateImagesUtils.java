package org.likelion._thon.silver_navi.global.util.geo;

import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateImagesUtils {

    public static List<String> updateImageFiles(
            S3Service s3Service,
            List<String> oldUrlsInDb,
            List<String> urlsToKeep,
            List<MultipartFile> images
    ) throws IOException {
        List<String> urlsToDelete = oldUrlsInDb.stream()
                .filter(oldUrl -> !urlsToKeep.contains(oldUrl))
                .toList();

        // S3 파일 삭제
        if (!urlsToDelete.isEmpty()) {
            for (String url : urlsToDelete) {
                s3Service.deleteFile(url);
            }
        }

        // S3 파일 업로드 실행
        List<String> newUploadedUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile img : images) {
                if (img != null && !img.isEmpty()) {
                    String newUrl = s3Service.uploadFile(img);
                    newUploadedUrls.add(newUrl);
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

        return finalImageUrls;
    }
}
