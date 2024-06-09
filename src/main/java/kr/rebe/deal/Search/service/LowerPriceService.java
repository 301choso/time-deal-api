package kr.rebe.deal.Search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.rebe.deal.dto.Keyword;
import kr.rebe.deal.dto.ProductDto;
import kr.rebe.deal.dto.ProductGrp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LowerPriceService {

    private final RedisTemplate myProdPriceRedis;
    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        myTempSet = myProdPriceRedis.opsForZSet().rangeWithScores(key,0, 9);
        return myTempSet;
    }


    public int setNewProduct(ProductDto newProduct) {
        int rank = 0;
        //myProdPriceRedis.opsForZSet().add("FDB", "25000", 123);
        myProdPriceRedis.opsForZSet().add(newProduct.getProdGrpId(), String.valueOf(newProduct.getProductSeq()), newProduct.getPrice().doubleValue());
        rank = myProdPriceRedis.opsForZSet().rank(newProduct.getProdGrpId(), String.valueOf(newProduct.getProductSeq())).intValue();
        return rank;
    }

    public int setNewProductGroup(ProductGrp newProductGrp) {
        List<ProductDto> productList = newProductGrp.getProductDtoList();
        Long prodocutSeq = productList.get(0).getProductSeq();
        Long price = productList.get(0).getPrice();
        myProdPriceRedis.opsForZSet().add(newProductGrp .getProdGrpId(), String.valueOf(prodocutSeq), price.doubleValue());
        int productCnt = myProdPriceRedis.opsForZSet().zCard(newProductGrp.getProdGrpId()).intValue();
        return productCnt;
    }

    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        myProdPriceRedis.opsForZSet().add(keyword, prodGrpId, score); // 기존에 있으면 넣고 없으면 안넣는다
        int rank = myProdPriceRedis.opsForZSet().rank(keyword, prodGrpId).intValue();
        return rank;
    }

    public Keyword getLowestPriceProductByKeyword(String keyword) {
         Keyword returnInfo = new Keyword();
         //keyword를 통해 productGroup 가져오기(10개)
        List<ProductGrp> teamProdGrp = new ArrayList<>();
        teamProdGrp = getProdUsingKeyword(keyword);

         // 가져온 정보들을 return할 object에 넣기
        returnInfo.setKeyword(keyword);
        returnInfo.setProductGrpList(teamProdGrp);
         return returnInfo;
    }

    public List<ProductGrp> getProdUsingKeyword(String keyword) {
        List<ProductGrp> returnInfo = new ArrayList<>();

        // input 받은 keyword로 producGrptId 조회
        List<String> prodGrpIdList = new ArrayList<>();
        prodGrpIdList = List.copyOf(myProdPriceRedis.opsForZSet().reverseRange(keyword,0, 9));

        List<ProductDto> tempProdList = new ArrayList<>();

        for(final String prodGrpId : prodGrpIdList) {

            ProductGrp teamProdGrp = new ProductGrp();
            // productGroup으로 Proudct:price가져오기(10개)
            Set prodAndPriceList = new HashSet();
            prodAndPriceList = myProdPriceRedis.opsForZSet().rangeWithScores(prodGrpId,0, 9);
            Iterator<Object> prodPriceObj = prodAndPriceList.iterator();
            //prdocut obj에 bind
            while (prodPriceObj.hasNext()) {
                ObjectMapper objMapper = new ObjectMapper();
                // {"value":00-1011-}, {"score":11000}
                Map<String, Object> prodPriceMap = objMapper.convertValue(prodPriceObj.next(), Map.class);
                ProductDto tempProduct = new ProductDto();
                // Prodcut obj bind
                String val = String.valueOf(prodPriceMap.get("value"));
                tempProduct.setProductSeq(Long.valueOf(Integer.valueOf(val)));
                double sco = (Double) prodPriceMap.get("score");
                tempProduct.setPrice((long) sco);
                tempProdList.add(tempProduct);
            }
            // 10개 product price 입력완료
            teamProdGrp.setProdGrpId(prodGrpId);
            teamProdGrp.setProductDtoList(tempProdList);
            returnInfo.add(teamProdGrp);
        }
        return returnInfo;
    }
}
