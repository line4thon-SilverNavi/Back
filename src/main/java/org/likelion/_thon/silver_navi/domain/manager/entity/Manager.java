package org.likelion._thon.silver_navi.domain.manager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.global.auth.UserRole;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manager")
public class Manager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false, unique = true)
    private NursingFacility nursingFacility; // FK

    public static Manager toEntity(String loginId, String encoded, NursingFacility nursingFacility) {
        return Manager.builder()
                .loginId(loginId)
                .password(encoded)
                .role(UserRole.ADMIN)
                .nursingFacility(nursingFacility)
                .build();
    }
}
