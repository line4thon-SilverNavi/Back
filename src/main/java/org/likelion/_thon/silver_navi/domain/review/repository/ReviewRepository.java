package org.likelion._thon.silver_navi.domain.review.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndNursingFacility(User user, NursingFacility nursingFacility);
    
    List<Review> findAllByNursingFacility(NursingFacility nursingFacility);

    List<Review> findByNursingFacility(NursingFacility nursingFacility);

    @Query("SELECT r FROM Review r " +
            "WHERE r.nursingFacility = :facility " +
            "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> findReviews(
            @Param("facility") NursingFacility facility,
            @Param("rating") Integer rating,
            Pageable pageable
    );
}