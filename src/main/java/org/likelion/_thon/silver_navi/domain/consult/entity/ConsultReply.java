package org.likelion._thon.silver_navi.domain.consult.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "consult_reply")
public class ConsultReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Setter(AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consult_id", nullable = true, unique = true)
    private Consult consult;

    @Setter(AccessLevel.PROTECTED)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_consult_id", nullable = true, unique = true)
    private GeneralConsult generalConsult;

    @Column(nullable = false)
    private String content; // 문의 답변 내용

    public static ConsultReply toEntity(String content, Consult consult, GeneralConsult generalConsult) {
        return ConsultReply.builder()
                .consult(consult)
                .generalConsult(generalConsult)
                .content(content)
                .build();
    }
}
