package org.likelion._thon.silver_navi.domain.program.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "program_apply")
public class ProgramApply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String rejectReason;

    public static ProgramApply create(User user, Program program, String content) {
        String finalContent = content != null && !content.trim().isEmpty() ? content.trim() : null;

        return ProgramApply.builder()
                .user(user)
                .program(program)
                .status(ApplicationStatus.PENDING)
                .content(finalContent)
                .build();
    }
}
