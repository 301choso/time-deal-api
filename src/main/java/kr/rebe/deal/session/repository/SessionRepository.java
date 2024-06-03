package kr.rebe.deal.session.repository;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 세션 Repository Interface
 * */
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findByAccessToken(String accessToken);

    List<Session> deleteAllByMember(Member member);

}
