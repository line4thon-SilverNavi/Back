package org.likelion._thon.silver_navi.domain.consult.repository;

import org.likelion._thon.silver_navi.domain.consult.entity.ConsultReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultReplyRepository extends JpaRepository<ConsultReply, Long> {

    boolean existsByConsult_Id(Long consultId);
    boolean existsByGeneralConsult_Id(Long generalConsultId);

    Optional<ConsultReply> findByConsult_Id(Long consultId);

    Optional<ConsultReply> findByGeneralConsult_Id(Long generalConsultId);
}
