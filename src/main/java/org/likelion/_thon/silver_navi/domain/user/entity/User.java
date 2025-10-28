package org.likelion._thon.silver_navi.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;
import org.likelion._thon.silver_navi.domain.user.entity.enums.UserRole;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationRole relation;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name ="search_radius")
    private Integer searchRadius;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static User toEntity(String name, String phone, RelationRole relation, String encoded){
        return User.builder()
                .name(name)
                .phone(phone)
                .relation(relation)
                .password(encoded)
                .searchRadius(5)
                .role(UserRole.USER)
                .build();
    }
}
