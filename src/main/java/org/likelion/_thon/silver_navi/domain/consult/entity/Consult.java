package org.likelion._thon.silver_navi.domain.consult.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultType;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultModifyNotAllowedException;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultConfirmReq;
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

    @Column(nullable = true)
    private String content;

    @Column(name = "confirmed_date", nullable = true)
    private LocalDate confirmedDate;        // 상담 확정일

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmed_time", nullable = true)
    private ConsultTime confirmedTime;      // 상담 확정 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private NursingFacility facility;

    @OneToOne(mappedBy = "consult", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private ConsultReply consultReply;

    public static Consult toEntity(ConsultApplyReq req, User user, NursingFacility facility) {
        return Consult.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .relationRole(req.getRelationRole())
                .hopeDate(req.getHopeDate())
                .hopeTime(req.getHopeTime())
                .consultType(req.getConsultType())
                .consultStatus(ConsultStatus.WAITING)
                .content((req.getContent() == null || req.getContent().isBlank()) ? null : req.getContent())
                .user(user)
                .facility(facility)
                .build();
    }

    public void updateConfirmation(ConsultConfirmReq req) {
        if (this.consultReply != null) {
            throw new ConsultModifyNotAllowedException();
        }

        if (req.getConfirmedDate() != null) {
            this.confirmedDate = req.getConfirmedDate();
        }
        if (req.getConfirmedTime() != null) {
            this.confirmedTime = req.getConfirmedTime();
        }
        if (req.getConsultStatus() != null) {
            if (req.getConsultStatus().equals(ConsultStatus.REJECTED)) {
                this.consultStatus = ConsultStatus.COMPLETED;
            } else this.consultStatus = req.getConsultStatus();
        }
    }

    // 연관관계 편의 메서드
    public void consultReply(ConsultReply consultReply) {
        this.consultReply = consultReply;

        if (consultReply != null) {
            consultReply.setConsult(this);
        }
    }
}
