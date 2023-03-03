package kr.rebe.deal.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {

    private String loginId;

    private String loginPwd;
}
