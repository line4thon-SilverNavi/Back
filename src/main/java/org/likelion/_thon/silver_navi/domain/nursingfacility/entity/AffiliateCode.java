package org.likelion._thon.silver_navi.domain.nursingfacility.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "affiliate_code")
public class AffiliateCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 랜덤 제휴 코드

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false, unique = true)
    private NursingFacility nursingFacility; // (FK)

    @Column(nullable = false)
    private LocalDateTime createdAt; // 코드 생성 시간

    @Column(nullable = false)
    private boolean used = false; // 사용 여부 (기본값 false)

    public static AffiliateCode toEntity(String encryptedCode, NursingFacility facility) {
        return AffiliateCode.builder()
                .code(encryptedCode)
                .nursingFacility(facility)
                .createdAt(LocalDateTime.now())
                .used(false)
                .build();
    }
}
