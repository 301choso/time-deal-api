package kr.rebe.deal.common.util;

import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;

@Slf4j
@RequiredArgsConstructor
public class AuthUtil {

    private static AuthService authService;

    public static MemberDto getAuth() {
        Cookie cookie = CookieUtil.getCookie("sessionAuth");
        if (cookie == null) {
            return null;
        }
        String value = cookie.getValue(); // 세션 값
        return authService.sessionMember(value).toDto();
    }
}
