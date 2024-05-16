package kr.rebe.deal.order.service;

import kr.rebe.deal.common.exception.CustomException;
import kr.rebe.deal.common.exception.ErrorCode;
import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.dto.OrdersDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Orders;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.order.repository.OrderRepository;
import kr.rebe.deal.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 구매 Service
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final RedissonClient redissonClient;

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
        Set<MemberDto> memberList = new HashSet<>();
        Product product = Product.builder().productSeq(productSeq).build();
        List<Orders> allByProduct = orderRepository.findAllByProduct(product);
        allByProduct.forEach(p -> memberList.add(p.getMember().toDto()));
        return new ArrayList<>(memberList);
    }

    /**
     * 구매하기
     */
    @Transactional
    public OrdersDto doOrder(OrdersDto ordersDto) {
        RLock lock = redissonClient.getLock("order");

        try {
            boolean isLocked = lock.tryLock(2,3, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new CustomException(ErrorCode.FAILED_GET_LOCK);
            }

            Product product = ordersDto.getProduct();
            validation(product);

            product.decreaseStock();
            productService.saveProduct(product.toDto());
            Orders saved = orderRepository.save(ordersDto.toEntity());

            return saved.toDto();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

        throw new CustomException(ErrorCode.FAILED_SALE);
    }

    /**
     * 구매하기 전 검증
     */
    public boolean validation(Product product) {
        if (product == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_PRODUCT);
        }
        if (!product.isSaleTime()) {
            throw new CustomException(ErrorCode.NOT_SALE_TIME);
        }
        if (!product.haveStock()) {
            throw new CustomException(ErrorCode.OUT_OF_STOCK);
        }
        return true;
    }
}
