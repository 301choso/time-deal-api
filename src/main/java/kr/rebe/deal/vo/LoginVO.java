package kr.rebe.deal.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginVO {

    private String loginId;

    private String loginPwd;
}
