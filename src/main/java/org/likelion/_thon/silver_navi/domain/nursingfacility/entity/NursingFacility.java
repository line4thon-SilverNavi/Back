package org.likelion._thon.silver_navi.domain.nursingfacility.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums.FacilityCategory;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "nursing_facility")
public class NursingFacility extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    // --- API 또는 목 데이터로 채울 기본 정보 ---
    @Column(nullable = false)
    private String name; // 시설명

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String facilityNumber; // 시설 번호

    @Column(nullable = false)
    private Double latitude; // 좌표값 (위도)

    @Column(nullable = false)
    private Double longitude; // 좌표값 (경도)

    // --- 관리자가 회원가입 후 채울 상세 정보 ---

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private FacilityCategory category; // 카테고리

    @Column(name = "operating_hours")
    private String operatingHours; // 운영 시간

    @Column(name = "description")
    private String description; // 시설 소개

    // '주요 서비스' 목록
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "facility_services", joinColumns = @JoinColumn(name = "facility_id"))
    @Column(name = "service")
    @Builder.Default
    private List<String> services = new ArrayList<>();

    // '시설 이미지' URL 목록
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "facility_images", joinColumns = @JoinColumn(name = "facility_id"))
    @Column(name = "image_uri")
    @Builder.Default
    private List<String> imageUris = new ArrayList<>();
}
