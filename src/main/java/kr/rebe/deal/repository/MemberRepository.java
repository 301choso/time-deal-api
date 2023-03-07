package kr.rebe.deal.repository;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.enums.YnEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 회원 Repository Interface
 * */

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    List<Member> findAll();

    Member findByLoginId(String loginId);

}
