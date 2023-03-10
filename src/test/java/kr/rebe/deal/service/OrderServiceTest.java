package kr.rebe.deal.service;

import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.dto.OrdersDto;
import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Orders;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.enums.LogInTypeEnum;
import kr.rebe.deal.enums.MethodEnum;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    ProductDto prodcutDto;

    MemberDto memberDto;

    OrdersDto ordersDto;

    Long productSeq, memberSeq, orderSeq;

    Product product;
    Orders orders;
    Member member;

    @BeforeEach
    void setUp() {
        prodcutDto = ProductDto.builder()
                .productName("제품명")
                .price(1000L)
                .discount(10L)
                .stock(10L)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now().minusDays(1))
                .build();
        ProductDto saveProduct = productService.saveProduct(prodcutDto);
        product = saveProduct.toEntity();
        productSeq = saveProduct.getProductSeq();

        memberDto = MemberDto.builder()
                .loginId("member1")
                .loginPwd("1234")
                .loginType(LogInTypeEnum.U)
                .regDate(LocalDateTime.now())
                .build();
        member = memberService.joinMember(memberDto);
        memberSeq = member.getMemberSeq();

        orders = Orders.builder()
                .product(saveProduct.toEntity())
                .member(member)
                .price(saveProduct.getPrice())
                .method(MethodEnum.CARD)
                .orderDate(LocalDateTime.now())
                .regDate(LocalDateTime.now())
                .build();
        orderSeq = orderRepository.save(orders).getOrderSeq();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteById(orderSeq);
        productService.deleteProduct(productSeq);
        memberRepository.deleteById(memberSeq);
    }

    @Test
    @DisplayName("구매 전체 목록 조회")
    void getOrdersList() {
        List<OrdersDto> orderList = orderService.getOrdersList();
        Assertions.assertTrue(orderList.size() > 0);
    }

    @Test
    @DisplayName("구매 번호로 단일 조회")
    void getOrders() {
        OrdersDto ordersDto = orderService.getOrders(orderSeq);
        Assertions.assertEquals(ordersDto.getOrderSeq(), orderSeq);
    }

    @Test
    @DisplayName("회원별 구매 목록 조회")
    void getOrdersByMember() {
        List<OrdersDto> orderList = orderService.getOrdersByMember(memberSeq);
        Assertions.assertTrue(orderList.size() > 0);
    }

    @Test
    @DisplayName("구매하기")
    void doOrder() {
        ordersDto = OrdersDto.builder()
                .product(product)
                .member(member)
                .price(prodcutDto.getPrice())
                .method(MethodEnum.CARD)
                .orderDate(LocalDateTime.now())
                .regDate(LocalDateTime.now())
                .build();
        OrdersDto ordersDto1 = orderService.doOrder(ordersDto);
        Assertions.assertEquals(ordersDto1.getProduct().getStock(), 9L);
        orderRepository.deleteById(ordersDto1.getOrderSeq());
    }
}