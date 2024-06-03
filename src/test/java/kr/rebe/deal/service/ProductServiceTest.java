package kr.rebe.deal.service;

import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.product.service.ProductService;
import kr.rebe.deal.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    Product product = Product.builder()
            .productName("productName")
            .price(1000L)
            .discount(50L)
            .stock(10L)
            .startDate(LocalDateTime.now().minusDays(1))
            .endDate(LocalDateTime.now().plusDays(1))
            .regDate(LocalDateTime.now())
            .build();

    Long productSeq;

    @Test
    @DisplayName("상품 목록 조회")
    void getProductList() {
        productSeq = productRepository.save(product).getProductSeq();
        List<ProductDto> productList = productService.getProductList();
        Assertions.assertTrue(productList.size() > 0);
        productRepository.deleteById(productSeq);
    }

    @Test
    @DisplayName("상품 단일 조회")
    void getProduct() {
        productSeq = productRepository.save(product).getProductSeq();
        ProductDto productInfo = productService.getProduct(product.getProductSeq());
        Assertions.assertEquals("productName", productInfo.getProductName());
        productRepository.deleteById(productSeq);
    }

    @Test
    @DisplayName("상품 등록")
    void saveProduct() {
        ProductDto saveProduct = ProductDto.builder()
                .productName("productNm")
                .price(1000L)
                .discount(50L)
                .stock(10L)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .build();
        ProductDto saved = productService.saveProduct(saveProduct);
        Assertions.assertEquals("productNm", saved.getProductName());
        productRepository.deleteById(saved.getProductSeq());
    }

    @Test
    @DisplayName("상품 수정")
    void updateProduct() {
        productSeq = productRepository.save(product).getProductSeq();
        ProductDto saveProduct = ProductDto.builder()
                .productSeq(productSeq)
                .productName("productNm")
                .price(2000L)
                .discount(50L)
                .stock(10L)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .build();
        ProductDto updated = productService.updateProduct(saveProduct);
        Assertions.assertEquals(2000L, updated.getPrice());
        productRepository.deleteById(productSeq);
    }

    @Test
    @DisplayName("삭제")
    void deleteProduct() {
        Product p = Product.builder()
                .productName("productName")
                .price(1000L)
                .discount(50L)
                .stock(10L)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .build();
        Product save = productRepository.save(p);
        boolean result = productService.deleteProduct(save.getProductSeq());
        Assertions.assertTrue(result);
    }
}