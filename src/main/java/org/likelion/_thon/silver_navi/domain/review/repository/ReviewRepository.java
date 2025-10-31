package org.likelion._thon.silver_navi.domain.review.repository;

import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
