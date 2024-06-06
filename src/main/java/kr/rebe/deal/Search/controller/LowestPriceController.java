package kr.rebe.deal.Search.controller;

import kr.rebe.deal.Search.service.LowerPriceService;
import kr.rebe.deal.dto.Keyword;
import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.dto.ProductGrp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LowestPriceController {

    private final LowerPriceService lowerPriceService;

    @GetMapping("/getZSETValue")
    public Set getZsetValue(String key) {
        return lowerPriceService.getZsetValue(key);

    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody ProductDto newProduct) {
        return lowerPriceService.setNewProduct(newProduct);
    }

    @PutMapping("/productGroup")
    public int SetNewProductGroup(@RequestBody ProductGrp newProductGrp) {
        return lowerPriceService.setNewProductGroup(newProductGrp);
    }

    @PutMapping("/productGroupTo Keyword")
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        return lowerPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
    }

    // 키워드에 맞는 상품 추천
    @GetMapping("/productPrice/lowest")
    public Keyword getLowestPriceProductByKeyword (String keyword) {
        return lowerPriceService.getLowestPriceProductByKeyword(keyword);
    }
}
