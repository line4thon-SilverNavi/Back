package org.likelion._thon.silver_navi.domain.manager.repository;

import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
