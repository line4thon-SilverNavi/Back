package org.likelion._thon.silver_navi.domain.caretarget.repository;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareTargetRepository extends JpaRepository<CareTarget, Long> {
    Optional<CareTarget> findByUser(User user);

}
