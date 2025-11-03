package org.likelion._thon.silver_navi.domain.consult.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultType;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CONSULTATION")
public class Consult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "relation_role")
    private RelationRole relationRole;

    @Column(nullable = false, name = "hope_date")
    private LocalDate hopeDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "hope_time")
    private ConsultTime hopeTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "consult_type")
    private ConsultType consultType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "consult_status")
    private ConsultStatus consultStatus;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private NursingFacility facility;

    public static Consult toEntity(ConsultApplyReq req, User user, NursingFacility facility) {
        return Consult.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .relationRole(req.getRelationRole())
                .hopeDate(req.getHopeDate())
                .hopeTime(req.getHopeTime())
                .consultType(req.getConsultType())
                .consultStatus(ConsultStatus.WAITING)
                .content(req.getContent())
                .user(user)
                .facility(facility)
                .build();
    }
}
