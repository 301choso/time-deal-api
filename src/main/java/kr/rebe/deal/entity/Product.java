package kr.rebe.deal.entity;

import kr.rebe.deal.dto.ProductDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSeq;

    private String productName;

    private Long price;

    private Long discount;

    private Long stock;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime regDate;

    @Builder
    public Product(Long productSeq, String productName, Long price, Long discount, Long stock, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime regDate) {
        this.productSeq = productSeq;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regDate = regDate;
    }

    public ProductDto toDto() {
        return ProductDto.builder()
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

    public void decreaseStock() {
        this.stock -= 1;
    }
    public boolean isSaleTime() {
        if (LocalDateTime.now().isAfter(this.startDate) &&
                LocalDateTime.now().isBefore(this.endDate)) {
            return true;
        }
        return false;
    }

    public boolean haveStock() {
       return this.stock > 0 ? true : false;
    }
}
