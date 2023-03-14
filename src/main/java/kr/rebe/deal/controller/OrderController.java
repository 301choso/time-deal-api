package kr.rebe.deal.controller;

import kr.rebe.deal.common.response.CommonResponse;
import kr.rebe.deal.common.util.AuthUtil;
import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.dto.OrdersDto;
import kr.rebe.deal.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 구매 Controller
 * */
@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController extends CommonResponse {

    private final OrderService orderService;

    /**
     * 구매 전체 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<OrdersDto>> getOrdersList() {
        AuthUtil.isAdmin();
        List<OrdersDto> ordersList = orderService.getOrdersList();
        return createResponseEntity(true, null, ordersList);
    }

    /**
     * 구매 번호로 단일 조회
     */
    @GetMapping("/order/{orderSeq}")
    public ResponseEntity<OrdersDto> getOrders(@PathVariable("orderSeq") Long orderSeq) {
        OrdersDto orders = orderService.getOrders(orderSeq);
        return createResponseEntity(true, null, orders);
    }

    /**
     * 회원별 구매 목록 조회
     * */
    @GetMapping("/member/{memberSeq}")
    public ResponseEntity<List<OrdersDto>> getOrdersByMember(@PathVariable("memberSeq") Long memberSeq) {
        AuthUtil.isAdminOrMember(memberSeq);
        List<OrdersDto> ordersList = orderService.getOrdersByMember(memberSeq);
        return createResponseEntity(true, null, ordersList);
    }

    /**
     * 상품별 구매한 회원 목록 조회
     * */
    @GetMapping("/product/{productSeq}")
    public ResponseEntity<List<MemberDto>> getMemberListByProduct(@PathVariable("productSeq") Long productSeq) {
        AuthUtil.isAdmin();
        List<MemberDto> memberList = orderService.getMemberListByProduct(productSeq);
        return createResponseEntity(true, null, memberList);
    }

    /**
     * 구매하기
     */
    @PostMapping
    public ResponseEntity<OrdersDto> doOrder(@ModelAttribute("ordersDto") OrdersDto ordersDto) {
        AuthUtil.isMember(ordersDto.getMember().getMemberSeq());
        OrdersDto ordered = orderService.doOrder(ordersDto);
        return createResponseEntity(true, null, ordered);
    }

}
