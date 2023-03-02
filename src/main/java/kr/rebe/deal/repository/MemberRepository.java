package kr.rebe.deal.repository;

import kr.rebe.deal.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 회원 Repository Interface
 * */

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByLoginIdAndLoginPwd(String loginId, String loginPwd);
}
