package kr.rebe.deal.dto;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword; // 유아용품 - 하기스기저귀(FPG0001), A사 딸랑이(FPG0002)

    private List<ProductGrp> productGrpList; // [{"FPG0001",[{"pid",25000}, {}, ...], "FPG0002"}]
}
