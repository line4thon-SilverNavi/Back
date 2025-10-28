package org.likelion._thon.silver_navi.domain.caretarget.web.dto;

import java.util.List;

public record OcrResult(
        List<ImageResult> images
) {
    public record ImageResult(List<Field> fields) {}

    public record Field(String inferText) {}
}
