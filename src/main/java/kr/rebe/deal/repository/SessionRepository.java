package kr.rebe.deal.repository;

import kr.rebe.deal.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 세션 Repository Interface
 * */
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

}
