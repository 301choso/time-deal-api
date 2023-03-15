package kr.rebe.deal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Orders;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.enums.MethodEnum;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrdersDto {

    private Long orderSeq;

    @JsonIgnore
    private Product product;

    @JsonIgnore
    private Member member;

    private Long price;

    private MethodEnum method;

    private LocalDateTime orderDate;

    private LocalDateTime regDate;

    @Builder
    public OrdersDto(Long orderSeq, Product product, Member member, Long price, MethodEnum method, LocalDateTime orderDate, LocalDateTime regDate) {
        this.orderSeq = orderSeq;
        this.product = product;
        this.member = member;
        this.price = price;
        this.method = method;
        this.orderDate = orderDate;
        this.regDate = regDate;
    }

    public Orders toEntity() {
        return Orders.builder()
                .orderSeq(orderSeq)
                .product(product)
                .member(member)
                .price(price)
                .method(method)
                .orderDate(orderDate)
                .regDate(LocalDateTime.now())
                .build();
    }
}
