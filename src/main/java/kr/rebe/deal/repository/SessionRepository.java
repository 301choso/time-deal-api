package kr.rebe.deal.repository;

import kr.rebe.deal.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 세션 Repository Interface
 * */
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> deleteAllByMemberSeq(Long memberSeq);

}
