package org.likelion._thon.silver_navi.domain.consult.web.dto;

import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.repository.CombinedConsultDto;
import org.likelion._thon.silver_navi.domain.consult.repository.ConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.repository.GeneralConsultRepository;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public record ConsultManagementRes(
        ConsultSummaryInfoRes summary,  // 상담 요약 정보
        List<ConsultInfoRes> consults,  // 상담 리스트
        PageInfo pageInfo
) {
    public record ConsultSummaryInfoRes(
            long totalCount,     // 총 상담 수
            long pendingCount,   // 대기중
            long approvedCount,  // 확인됨 (상담사가 내용을 열어본 상태)
            long completedCount  // 완료 (상담까지 모두 마친 상태)
    ) {
        public static ConsultSummaryInfoRes of(
                Long facilityId,
                GeneralConsultRepository generalConsultRepository,
                ConsultRepository consultRepository,
                Page<CombinedConsultDto> consults
        ) {
            long totalCount = consults.getTotalElements();
            long pendingCount = generalConsultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.WAITING) +
                    consultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.WAITING);
            long approvedCount = generalConsultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.CONFIRMED) +
                    consultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.CONFIRMED);
            long completedCount = generalConsultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.COMPLETED) +
                    consultRepository.countByFacilityIdAndConsultStatus(facilityId, ConsultStatus.COMPLETED);

            return new ConsultSummaryInfoRes(
                    totalCount,
                    pendingCount,
                    approvedCount,
                    completedCount
            );
        }
    }

    public record ConsultInfoRes(
            Long consultId,                     // 상담 PK
            LocalDate consultDate,              // 신청일
            ConsultCategory consultCategory,    // 상담 유형 (일반상담, 시설상담)
            String name,                        // 신청자
            RelationRole relationRole,          // 관계
            String phone,                       // 연락처
            ConsultStatus status                // 상태
    ) {
        public static ConsultInfoRes from(CombinedConsultDto consult) {
            return new ConsultInfoRes(
                    consult.getOriginalId(),
                    consult.getCreatedAt().toLocalDate(),
                    consult.getConsultCategory(),
                    consult.getName(),
                    consult.getRelationRole(),
                    consult.getPhone(),
                    consult.getStatus()
            );
        }
    }

    public record PageInfo(
            int totalPages,     // 전체 페이지 수
            long totalElements, // 전체 신청 수 개수
            int currentPage,    // 현재 페이지 번호
            int pageSize        // 페이지 당 아이템 수
    ) {
        public static PageInfo from(Page<ConsultInfoRes> consultInfoPage) {
            return new PageInfo(
                    consultInfoPage.getTotalPages(),    // 전체 페이지 수
                    consultInfoPage.getTotalElements(), // 전체 아이템 개수
                    consultInfoPage.getNumber() + 1,    // 현재 페이지 번호
                    consultInfoPage.getSize()           // 페이지 당 아이템 수 (5)
            );
        }
    }
}
