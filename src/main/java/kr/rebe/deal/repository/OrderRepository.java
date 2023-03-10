package kr.rebe.deal.repository;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {
    List<Orders> findAllByMember(Member member);
}
