package org.likelion._thon.silver_navi.domain.user.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

public record UserMypageInfoRes(
        String name,
        RelationRole relation,
        String phone,
        int bookmarkCount,
        int consultCount,
        int reviewCount,
        boolean hasCareTarget
) {
    public static UserMypageInfoRes from(User user) {
        int bookmarkCount = user.getProgramBookmarks().size() + user.getFacilityBookmarks().size();
        int consultCount = user.getConsults().size() + user.getGeneralConsults().size();
        int reviewCount = user.getReviews().size();

        boolean hasCompleteCareTarget = false;
        CareTarget careTarget = user.getCareTarget();
        //모든 정보가 있는지 확인
        if (careTarget != null) {
            boolean allFilled =
                    careTarget.getName() != null &&
                            careTarget.getBirthDate() != null &&
                            careTarget.getGender() != null &&
                            careTarget.getCareGrade() != null &&
                            careTarget.getCareNumber() != null;
            hasCompleteCareTarget = allFilled;
        }

        return new UserMypageInfoRes(
                user.getName(),
                user.getRelation(),
                user.getPhone(),
                bookmarkCount,
                consultCount,
                reviewCount,
                hasCompleteCareTarget
        );
    }
}
