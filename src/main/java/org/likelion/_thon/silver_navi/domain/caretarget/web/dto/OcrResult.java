package org.likelion._thon.silver_navi.domain.caretarget.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OcrResult(
        List<ImageResult> images
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ImageResult(List<Field> fields) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Field(String inferText) {}
}
