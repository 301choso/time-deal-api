package kr.rebe.deal.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductGrp {

    private String prodGrpId; // FPC0001

    private List<ProductDto> productDtoList; // [{"pid",25000}, {}, ...]

}
