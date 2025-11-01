package org.likelion._thon.silver_navi.domain.consult.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GENERAL_CONSULTATION")
public class GeneralConsult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false, name = "inquiry_type")
    private String inquiryType;

    @Column(nullable = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private NursingFacility facility;

    public static GeneralConsult toEntity(GeneralApplyReq req, User user, NursingFacility facility) {
        return GeneralConsult.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .email(req.getEmail())           // null 가능
                .inquiryType(req.getInquiryType())
                .content(req.getContent())       // null 가능
                .user(user)
                .facility(facility)
                .build();
    }
}
