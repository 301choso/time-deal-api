package kr.rebe.deal.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 쿠키 조회
     * */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    /**
     *  쿠키 설정
     * */
    public static void setCookie(HttpServletResponse response, String sessionAuth) {
        Cookie cookie = new Cookie("sessionAuth", sessionAuth);
        response.addCookie(cookie);
    }
}
