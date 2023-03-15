package kr.rebe.deal.entity;

import kr.rebe.deal.dto.OrdersDto;
import kr.rebe.deal.enums.MethodEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    private Long price;

    @Enumerated(EnumType.STRING)
    private MethodEnum method;

    private LocalDateTime orderDate;

    private LocalDateTime regDate;

    @Builder
    public Orders(Long orderSeq, Product product, Member member, Long price, MethodEnum method, LocalDateTime orderDate, LocalDateTime regDate) {
        this.orderSeq = orderSeq;
        this.product = product;
        this.member = member;
        this.price = price;
        this.method = method;
        this.orderDate = orderDate;
        this.regDate = regDate;
    }

    public OrdersDto toDto() {
        return OrdersDto.builder()
                .orderSeq(orderSeq)
                .product(product)
                .member(member)
                .price(price)
                .method(MethodEnum.CARD)
                .orderDate(orderDate)
                .regDate(LocalDateTime.now())
                .build();
    }
}
