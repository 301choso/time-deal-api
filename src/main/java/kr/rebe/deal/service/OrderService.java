package kr.rebe.deal.service;

import kr.rebe.deal.common.exception.CustomException;
import kr.rebe.deal.common.exception.ErrorCode;
import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.dto.OrdersDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Orders;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 구매 Service
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 구매 전체 목록 조회
     */
    public List<OrdersDto> getOrdersList() {
        List<OrdersDto> ordersList = new ArrayList<>();
        orderRepository.findAll().forEach(o-> ordersList.add(o.toDto()));
        return ordersList;
    }

    /**
     * 구매 번호로 단일 조회
     */
    public OrdersDto getOrders(Long orderSeq) {
        Orders orders = orderRepository.findById(orderSeq).orElse(null);
        return orders.toDto();
    }

    /**
     * 회원별 구매 목록 조회
     * */
    public List<OrdersDto> getOrdersByMember(Long memberSeq) {
        List<OrdersDto> ordersList = new ArrayList<>();
        Member member = Member.builder()
                .memberSeq(memberSeq)
                .build();
        orderRepository.findAllByMember(member).forEach(o-> ordersList.add(o.toDto()));
        return ordersList;
    }

    /**
     * 상품별 구매한 회원 목록 조회
     * */
    public List<MemberDto> getMemberListByProduct(Long productSeq) {
        List<MemberDto> memberList = new ArrayList<>();
        Product product = Product.builder().productSeq(productSeq).build();
        List<Orders> allByProduct = orderRepository.findAllByProduct(product);
        allByProduct.forEach(p -> memberList.add(p.getMember().toDto()));
        return memberList;
    }

    /**
     * 구매하기
     */
    @Transactional
    public OrdersDto doOrder(OrdersDto ordersDto) {
        // 제품이 존재하는지
        Product product = ordersDto.getProduct();
        if (product == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_PRODUCT);
        }
        // 구매 가능한 시간인지?
        if (!LocalDateTime.now().isAfter(product.getStartDate()) ||
            !LocalDateTime.now().isBefore(product.getEndDate())) {
            throw new CustomException(ErrorCode.NOT_SALE_TIME);
        }
        // 재고가 있는지?
        if (product.getStock() <= 0) {
            throw new CustomException(ErrorCode.OUT_OF_STOCK);
        }

        // 있으면 저장
        Orders saved = orderRepository.save(ordersDto.toEntity());

        //재고 차감
        product.decreaseStock();

        return saved.toDto();
    }
}
