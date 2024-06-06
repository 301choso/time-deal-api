package kr.rebe.deal.dto;

import kr.rebe.deal.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ProductDto {

    private String prodGrpId;

    private Long productSeq;

    private String productName;

    private Long price;

    private Long discount;

    private Long stock;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime regDate;

    @Builder
    public ProductDto(Long productSeq, String productName, Long price, Long discount, Long stock, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime regDate) {
        this.productSeq = productSeq;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regDate = regDate;
    }

    public Product toEntity() {
        return Product.builder()
                .productSeq(productSeq)
                .productName(productName)
                .price(price)
                .discount(discount)
                .stock(stock)
                .startDate(startDate)
                .endDate(endDate)
                .regDate(LocalDateTime.now())
                .build();
    }
}
