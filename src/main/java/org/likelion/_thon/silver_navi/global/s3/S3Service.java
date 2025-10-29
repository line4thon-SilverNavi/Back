package org.likelion._thon.silver_navi.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * S3에 이미지 업로드 하기
     */
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    // ----------------------------------------------------------------------------------------------------------

    public void deleteImage(String fileUrl) {
        try {
            // S3의 파일 이름 URL에서 추출
            String fileName = getFileNameFromUrl(fileUrl);
            // S3에서 파일을 삭제
            amazonS3.deleteObject(bucket, fileName);
        } catch (Exception e) {
            // 삭제 실패 시 로그
        }
    }

    private String getBaseUrl() {
        return String.format("https://%s.s3.%s.amazonaws.com/", bucket, amazonS3.getRegionName());
    }

    private String getFileNameFromUrl(String fileUrl) {
        String baseUrl = getBaseUrl();
        if (fileUrl != null && fileUrl.startsWith(baseUrl)) {
            return fileUrl.substring(baseUrl.length());
        }
        return "";
    }
}
