package org.likelion._thon.silver_navi.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.user.entity.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "facility_bookmark",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "facility_id"}
                )
        }
)
public class FacilityBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private NursingFacility facility;

    public static FacilityBookmark create(User user, NursingFacility facility) {
        return FacilityBookmark.builder()
                .user(user)
                .facility(facility)
                .build();
    }
}
