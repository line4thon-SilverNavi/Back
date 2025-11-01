package org.likelion._thon.silver_navi.domain.consult.repository;

import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
}
