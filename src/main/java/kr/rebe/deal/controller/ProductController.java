package kr.rebe.deal.controller;

import kr.rebe.deal.common.response.CommonResponse;
import kr.rebe.deal.common.util.AuthUtil;
import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 controller
 * */
@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController extends CommonResponse {

    private final ProductService productService;

    /**
     * 상품 목록 조회
     * */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductList() {
        List<ProductDto> productList = productService.getProductList();
        return createResponseEntity(true, null, productList);
    }

    /**
     * 상품 단일 조회
     * */
    @GetMapping("/{productSeq}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productSeq") Long productSeq) {
        ProductDto product = productService.getProduct(productSeq);
        return createResponseEntity(true, null, product);
    }

    /**
     * 상품 등록
     * */
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@ModelAttribute("productDto") ProductDto productDto) {
        AuthUtil.isAdmin();
        ProductDto saveProduct = productService.saveProduct(productDto);
        return createResponseEntity(true, null, saveProduct);
    }

    /**
     * 상품 수정
     * */
    @PostMapping("/{productSeq}")
    public ResponseEntity<ProductDto> updateProduct(@ModelAttribute("productDto") ProductDto productDto) {
        AuthUtil.isAdmin();
        ProductDto saveProduct = productService.updateProduct(productDto);
        return createResponseEntity(true, null, saveProduct);
    }
    /**
     * 상품 삭제
     * */
    @DeleteMapping("/{productSeq}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("productSeq") Long productSeq) {
        AuthUtil.isAdmin();
        boolean isSuccess = productService.deleteProduct(productSeq);
        return createResponseEntity(isSuccess, null);
    }
}
