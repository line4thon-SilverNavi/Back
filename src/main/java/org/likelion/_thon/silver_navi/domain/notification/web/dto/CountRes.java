package org.likelion._thon.silver_navi.domain.notification.web.dto;

public record CountRes(
        int count
) {
    public static CountRes from(int count) {
        return new CountRes(count);
    }
}
