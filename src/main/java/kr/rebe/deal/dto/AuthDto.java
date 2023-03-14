package kr.rebe.deal.dto;

import kr.rebe.deal.enums.LogInTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {

    private Long memberSeq;

    private String memberName;

    private LogInTypeEnum loginType;

    private String loginId;

}
