package org.likelion._thon.silver_navi.domain.consult.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.InquiryType;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultModifyNotAllowedException;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultConfirmReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.time.LocalDate;

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

    @Column(nullable = false, name = "inquiry_type")
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Column(nullable = true)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "consult_status")
    private ConsultStatus consultStatus;

    @Column(name = "confirmed_date", nullable = true)
    private LocalDate confirmedDate;        // 상담 확정일

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmed_time", nullable = true)
    private ConsultTime confirmedTime;      // 상담 확정 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private NursingFacility facility;

    @OneToOne(mappedBy = "generalConsult", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private ConsultReply consultReply;

    public static GeneralConsult toEntity(GeneralApplyReq req, User user, NursingFacility facility) {
        return GeneralConsult.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .inquiryType(req.getInquiryType())
                .consultStatus(ConsultStatus.WAITING)
                .content(req.getContent())       // null 가능
                .user(user)
                .facility(facility)
                .build();
    }

    public void updateConfirmation(ConsultConfirmReq req) {
        if (this.consultReply != null) {
            throw new ConsultModifyNotAllowedException();
        }

        if (req.getConsultStatus().equals(ConsultStatus.REJECTED)) {
            this.confirmedDate = null;
            this.confirmedTime = null;
            this.consultStatus = ConsultStatus.COMPLETED;
        } else {
            if (req.getConfirmedDate() != null) {
                this.confirmedDate = req.getConfirmedDate();
            }
            if (req.getConfirmedTime() != null) {
                this.confirmedTime = req.getConfirmedTime();
            }
            if (req.getConsultStatus() != null) {
                this.consultStatus = req.getConsultStatus();
            }
        }
    }

    // 연관관계 편의 메서드
    public void consultReply(ConsultReply consultReply) {
        this.consultReply = consultReply;

        if (consultReply != null) {
            consultReply.setGeneralConsult(this);
        }
    }
}
