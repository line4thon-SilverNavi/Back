package org.likelion._thon.silver_navi.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating; // 별점 (0.0 ~ 5.0)

    @Column(nullable = false, length = 1000)
    private String content; // 후기 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private NursingFacility nursingFacility;

    public static Review create(User user, NursingFacility facility, BigDecimal rating, String content) {
        return Review.builder()
                .user(user)
                .nursingFacility(facility)
                .rating(rating)
                .content(content)
                .build();
    }
}