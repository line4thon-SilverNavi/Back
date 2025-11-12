package org.likelion._thon.silver_navi.domain.user.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

public record UserMypageInfoRes(
        String guardianName,
        RelationRole relation,
        String guardianPhone,
        String careTargetName,
        String careTargetPhone,
        LocalDate careTargetBirth,
        Gender careTargetGender,
        int bookmarkCount,
        int consultCount,
        int reviewCount,
        boolean hasCareTarget
) {
    public static UserMypageInfoRes from(User user) {
        int bookmarkCount = user.getProgramBookmarks().size() + user.getFacilityBookmarks().size();
        int consultCount = user.getConsults().size() + user.getGeneralConsults().size();
        int reviewCount = user.getReviews().size();

        String careTargetName = null;
        String careTargetPhone = null;
        LocalDate careTargetBirth = null;
        Gender careTargetGender = null;
        boolean hasCompleteCareTarget = false;
        CareTarget careTarget = user.getCareTarget();

        //모든 정보가 있는지 확인
        if (careTarget != null) {
            careTargetName = careTarget.getName();
            careTargetBirth = careTarget.getBirthDate();
            careTargetGender = careTarget.getGender();
            careTargetPhone = careTarget.getPhoneNumber();

            boolean allFilled =
                    careTarget.getName() != null &&
                            careTarget.getBirthDate() != null &&
                            careTarget.getGender() != null &&
                            careTarget.getCareGrade() != null &&
                            careTarget.getPhoneNumber() != null;
            hasCompleteCareTarget = allFilled;
        }

        return new UserMypageInfoRes(
                user.getName(),
                user.getRelation(),
                user.getPhone(),
                careTargetName,
                careTargetPhone,
                careTargetBirth,
                careTargetGender,
                bookmarkCount,
                consultCount,
                reviewCount,
                hasCompleteCareTarget
        );
    }
}
