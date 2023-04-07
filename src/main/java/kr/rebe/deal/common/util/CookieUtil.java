package kr.rebe.deal.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {

    /**
     * 쿠키 조회
     * */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return Optional.of(cookie);
        }
        return Optional.empty();
    }

    /**
     *  쿠키 설정
     * */
    public static void setCookie(HttpServletResponse response, String sessionAuth) {
        Cookie cookie = new Cookie("sessionAuth", sessionAuth);
        response.addCookie(cookie);
    }
}
