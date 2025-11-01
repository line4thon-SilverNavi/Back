package org.likelion._thon.silver_navi.domain.caretarget.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.web.dto.UserUpdateReq;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CARE_TARGETS")
public class CareTarget extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "care_grade")
    private CareGrade careGrade;

    @Column(name = "care_number", unique = true)
    private String careNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static CareTarget create(UserUpdateReq dto, User user) {
        CareTarget careTarget = new CareTarget();
        careTarget.updatePartial(dto);
        careTarget.setUser(user);
        return careTarget;
    }

    public void updatePartial(UserUpdateReq dto) {
        if (dto.getCareTargetName() != null) this.name = dto.getCareTargetName();
        if (dto.getCareTargetBirth() != null) this.birthDate = dto.getCareTargetBirth();
        if (dto.getCareTargetGender() != null) this.gender = dto.getCareTargetGender();
        if (dto.getAddress() != null) this.address = dto.getAddress();
        if (dto.getCareNumber() != null) this.careNumber = dto.getCareNumber();
        if (dto.getCareGrade() != null) this.careGrade = dto.getCareGrade();
    }
}
