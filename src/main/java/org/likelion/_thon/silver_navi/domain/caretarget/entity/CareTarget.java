package org.likelion._thon.silver_navi.domain.caretarget.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CARETARGETS")
public class CareTarget {
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
}
