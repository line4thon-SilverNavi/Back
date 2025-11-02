package org.likelion._thon.silver_navi.domain.nursingfacility.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums.FacilityCategory;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityModifyReq;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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

    // 평균 별점 (리뷰 등록 시마다 업데이트)
    @Column(name = "average_rating", precision = 3, scale = 2, nullable = true)
    private BigDecimal averageRating;

    // 리뷰 수 (리뷰 등록 시마다 증가/감소)
    @Column(name = "review_count", nullable = true)
    private Long reviewCount;

    // --- 양방향 ---
    @OneToOne(
            mappedBy = "nursingFacility",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,    // 시설 삭제 시 관리자도 삭제
            orphanRemoval = true          // 관계가 끊어진 Manager 자동 삭제
    )
    private Manager manager;

    @OneToMany(
            mappedBy = "nursingFacility",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneralConsult> generalConsults = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consult> consults = new ArrayList<>();

    // '주요 서비스' 목록
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "facility_services", joinColumns = @JoinColumn(name = "facility_id"))
    @Column(name = "service")
    @Builder.Default
    private List<String> services = new ArrayList<>();

    // '시설 이미지' URL 목록
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "facility_images", joinColumns = @JoinColumn(name = "facility_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    public NursingFacility updateEntity(
            NursingFacilityModifyReq req, List<String> finalImageUrls
    ) {
        if (StringUtils.hasText(req.getName())) {
            this.name = req.getName();
        }
        if (StringUtils.hasText(req.getCategory())) {
            this.category = FacilityCategory.fromValue(req.getCategory());
        }
        if (req.getOperatingHours() != null) {
            this.operatingHours = req.getOperatingHours().isBlank() ? null : req.getOperatingHours();
        }
        if (req.getNumber() != null) {
            this.facilityNumber = req.getNumber().isBlank() ? null : req.getNumber();
        }
        if (StringUtils.hasText(req.getAddress())) {
            this.address = req.getAddress();
        }
        if (req.getDescription() != null) {
            this.description = req.getDescription().isBlank() ? null : req.getDescription();
        }
        if (req.getMainServices() != null) {
            this.services.clear();
            List<String> mainServices = req.getMainServices().stream()
                    .filter(StringUtils::hasText) // null, "", " " 필터링
                    .toList();
            this.services.addAll(mainServices);
        }

        // 사진
        this.imageUrls.clear();
        if (finalImageUrls != null) {
            this.imageUrls.addAll(finalImageUrls);
        }

        return this;
    }

    public void update(){
        this.averageRating = BigDecimal.ZERO;
        this.reviewCount = 0L;
    }
}
