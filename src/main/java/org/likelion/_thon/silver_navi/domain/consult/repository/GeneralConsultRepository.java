package org.likelion._thon.silver_navi.domain.consult.repository;

import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralConsultRepository extends JpaRepository<GeneralConsult, Long> {
    List<GeneralConsult> findAllByUser(User user);

    // 상담 상태별 개수 가져오기
    long countByFacilityIdAndConsultStatus(Long facilityId, ConsultStatus consultStatus);
}
