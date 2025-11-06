package org.likelion._thon.silver_navi.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.bookmark.entity.FacilityBookmark;
import org.likelion._thon.silver_navi.domain.bookmark.entity.ProgramBookmark;
import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;
import org.likelion._thon.silver_navi.domain.user.web.dto.LocationUpdateReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.UserUpdateReq;
import org.likelion._thon.silver_navi.global.auth.UserRole;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private CareTarget careTarget;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneralConsult> generalConsults = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consult> consults = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramApply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramBookmark> programBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacilityBookmark> facilityBookmarks = new ArrayList<>();

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

    public void updatePartial(UserUpdateReq dto) {
        if (dto.getUserName() != null) this.name = dto.getUserName();
        if (dto.getRelationRole() != null) this.relation = dto.getRelationRole();
    }

    public void updateRadius(Integer searchRadius) {
        this.searchRadius = searchRadius;
    }

    public void updateLocation(LocationUpdateReq req) {
        this.latitude = req.getLatitude();
        this.longitude = req.getLongitude();
    }
}
