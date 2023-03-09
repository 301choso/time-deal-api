package kr.rebe.deal.service;

import kr.rebe.deal.common.exception.CustomException;
import kr.rebe.deal.common.exception.ErrorCode;
import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.entity.Product;
import kr.rebe.deal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 목록 조회
     * */
    public List<ProductDto> getProductList() {
        List<ProductDto> productList = new ArrayList<>();
        productRepository.findAll().forEach(p -> productList.add(p.toDto()));
        return productList;
    }

    /**
     * 상품 단일 조회
     * */
    public ProductDto getProduct(Long productSeq) {
        Product product = productRepository.findById(productSeq).orElse(null);
        return product.toDto();
    }

    /**
     * 상품 등록
     * */
    public ProductDto saveProduct(ProductDto productDto) {
        Product saveProduct = productRepository.save(productDto.toEntity());
        return saveProduct.toDto();
    }

    /**
     * 상품 수정
     * */
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Long productSeq = productDto.getProductSeq();
        Product product = productRepository.findById(productSeq).orElse(null);
        if (product == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_PRODUCT);
        }
        Product saveProduct = productRepository.save(productDto.toEntity());
        return saveProduct.toDto();
    }

    /**
     * 상품 삭제
     * */
    @Transactional
    public boolean deleteProduct(Long productSeq) {
        Product product = productRepository.findById(productSeq).orElse(null);
        if (product == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_PRODUCT);
        }
        productRepository.deleteById(productSeq);
        return true;
    }
}
