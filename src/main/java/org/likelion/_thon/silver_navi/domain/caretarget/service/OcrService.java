package org.likelion._thon.silver_navi.domain.caretarget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.likelion._thon.silver_navi.domain.caretarget.exception.OcrRequestFailedException;
import org.likelion._thon.silver_navi.domain.caretarget.web.dto.OcrRes;
import org.likelion._thon.silver_navi.domain.caretarget.web.dto.OcrResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {

    @Value("${clova.ocr.api.url}")
    private String clovaOcrUrl;

    @Value("${clova.ocr.secret-key}")
    private String clovaSecretKey;

    private final RestTemplate restTemplate;

    public OcrRes requestClovaOcr(String imageUrl) {
       try {
           // 요청 바디 생성
           JSONObject requestBody = new JSONObject();
           requestBody.put("version", "V2");
           requestBody.put("requestId", UUID.randomUUID().toString());
           requestBody.put("timestamp", System.currentTimeMillis());

           // images 배열 생성
           JSONObject imageObj = new JSONObject();
           imageObj.put("format", "jpg");
           imageObj.put("name", "care-target-ocr");
           imageObj.put("url", imageUrl);

           requestBody.put("images", List.of(imageObj));

           // 헤더 설정
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.set("X-OCR-SECRET", clovaSecretKey);

           // 요청 생성
           HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

           // 요청 실행
           ResponseEntity<OcrResult> response = restTemplate.exchange(
                   clovaOcrUrl,
                   HttpMethod.POST,
                   entity,
                   OcrResult.class
           );

           return parseOcrResult(response.getBody());

       }catch (RestClientException e) {
            log.error("[Clova OCR] 요청 실패 - {}", e.getMessage(), e);
            throw  new OcrRequestFailedException();
        }
    }

    //ocr 전체 결과에서 인정 번호, 등급 추출
    private OcrRes parseOcrResult(OcrResult ocrResult) {
        if (ocrResult == null || ocrResult.images() == null || ocrResult.images().isEmpty()) {
            return new OcrRes(null);
        }

        // 모든 inferText를 공백으로 이어 붙이기
        String fullText = ocrResult.images().getFirst().fields().stream()
                .map(OcrResult.Field::inferText)
                .collect(Collectors.joining(" "));

        // 패턴 매칭 (인정번호: 2024-123456 / 등급: 2등급)
//        Pattern numberPattern = Pattern.compile(
//                "(장기요양(?:인정번호)?|인정번호)\\s*[:：-]?\\s*([0-9]{4}\\s*[-–—]?\\s*[0-9]{6})"
//        );
        Pattern gradePattern = Pattern.compile("장기요양등급\\s*([0-9]+등급)");

        //탐색
        //Matcher numberMatcher = numberPattern.matcher(fullText);
        Matcher gradeMatcher = gradePattern.matcher(fullText);

        //결과 반환
        //String careNumber = numberMatcher.find() ? numberMatcher.group(2) : null;
        String careGrade = gradeMatcher.find() ? gradeMatcher.group(1) : null;

        return new OcrRes(careGrade);
    }
}
