package org.likelion._thon.silver_navi.domain.consult.repository;

import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    List<Consult> findAllByUser(User user);

    // 상담 상태별 개수 가져오기
    long countByFacilityIdAndConsultStatus(Long facilityId, ConsultStatus consultStatus);

    @Query(
            value = "SELECT " +
                    "    CONCAT('GENERAL_', id) AS compositeId, " +
                    "    created_at AS createdAt, " +
                    "    'GENERAL' AS consultCategory, " +
                    "    name AS name, " +
                    "    NULL AS relationRole, " +
                    "    phone AS phone, " +
                    "    consult_status AS status, " +
                    "    id AS originalId " +
                    "FROM general_consultation " +
                    "WHERE facility_id = :facilityId AND (:status IS NULL OR consult_status = :status) " +
                    "UNION ALL " +
                    "SELECT " +
                    "    CONCAT('GRADE_', id) AS compositeId, " +
                    "    created_at AS createdAt, " +
                    "    'GRADE' AS consultCategory, " +
                    "    name AS name, " +
                    "    relation_role AS relationRole, " +
                    "    phone AS phone, " +
                    "    consult_status AS status, " +
                    "    id AS originalId " +
                    "FROM consultation " +
                    "WHERE facility_id = :facilityId AND (:status IS NULL OR consult_status = :status) ",

            countQuery = "SELECT COUNT(*) FROM (" +
                    "   SELECT id FROM general_consultation " +
                    "   WHERE facility_id = :facilityId AND (:status IS NULL OR consult_status = :status) " +
                    "   UNION ALL " +
                    "   SELECT id FROM consultation " +
                    "   WHERE facility_id = :facilityId AND (:status IS NULL OR consult_status = :status) " +
                    ") AS combined_count",

            nativeQuery = true
    )
    Page<CombinedConsultDto> findCombinedConsultByFacilityId(
            @Param("facilityId") Long facilityId,
            @Param("status") ConsultStatus status,
            Pageable pageable
    );


    @Query(
            value = "SELECT " +
                    "    CONCAT('GENERAL_', id) AS compositeId, " +
                    "    created_at AS createdAt, " +
                    "    'GENERAL' AS consultCategory, " +
                    "    name AS name, " +
                    "    NULL AS relationRole, " +
                    "    phone AS phone, " +
                    "    consult_status AS status, " +
                    "    id AS originalId " +
                    "FROM general_consultation " +
                    "WHERE facility_id = :facilityId " +
                    "  AND (:keyword IS NULL OR name LIKE CONCAT('%', :keyword, '%') OR phone LIKE CONCAT('%', :keyword, '%')) " +
                    "UNION ALL " +
                    "SELECT " +
                    "    CONCAT('GRADE_', id) AS compositeId, " +
                    "    created_at AS createdAt, " +
                    "    'GRADE' AS consultCategory, " +
                    "    name AS name, " +
                    "    relation_role AS relationRole, " +
                    "    phone AS phone, " +
                    "    consult_status AS status, " +
                    "    id AS originalId " +
                    "FROM consultation " +
                    "WHERE facility_id = :facilityId " +
                    "  AND (:keyword IS NULL OR name LIKE CONCAT('%', :keyword, '%') OR phone LIKE CONCAT('%', :keyword, '%')) " +
                    "ORDER BY createdAt DESC",
            nativeQuery = true
    )
    List<CombinedConsultDto> findCombinedConsultList(
            @Param("facilityId") Long facilityId,
            @Param("keyword") String keyword
    );
}
