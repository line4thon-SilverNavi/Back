package org.likelion._thon.silver_navi.domain.caretarget.web.controller;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.caretarget.service.OcrService;
import org.likelion._thon.silver_navi.domain.caretarget.web.dto.OcrRes;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.likelion._thon.silver_navi.global.s3.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/caretargets")
@RequiredArgsConstructor
public class CareTargetController implements CareTargetApi {
    private final S3Service s3Service;
    private final OcrService ocrService;

    // OCR
    @Override
    @PostMapping("/ocr")
    public ResponseEntity<SuccessResponse<OcrRes>> analyzeOcr(@RequestParam MultipartFile image) throws IOException {
        String imageUrl = s3Service.uploadImage(image); // S3 업로드
        OcrRes result = ocrService.requestClovaOcr(imageUrl); // URL로 OCR 요청
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(result));
    }
}
