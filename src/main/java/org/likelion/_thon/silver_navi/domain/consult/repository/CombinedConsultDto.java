package org.likelion._thon.silver_navi.domain.consult.repository;

import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDateTime;

public interface CombinedConsultDto {
    String getCompositeId();                // 인조 ID (예: 'GENERAL_1', 'CONSULT_1')
    ConsultCategory getConsultCategory();   // 상담 카테고리 (일반상담, 상담)
    LocalDateTime getCreatedAt();           // 상담 신청일
    String getName();                       // 신청자 이름
    RelationRole getRelationRole();         // 관계
    String getPhone();                      // 신청자 전화번호
    ConsultStatus getStatus();              // 상담 상태 (대기중, 확인됨, 완료, 거부)
    Long getOriginalId();                   // 원본테이블의 실제 ID
}
