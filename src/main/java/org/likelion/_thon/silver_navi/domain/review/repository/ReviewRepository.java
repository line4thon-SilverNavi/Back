package org.likelion._thon.silver_navi.domain.review.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndNursingFacility(User user, NursingFacility nursingFacility);
    
    List<Review> findAllByNursingFacility(NursingFacility nursingFacility);
}
