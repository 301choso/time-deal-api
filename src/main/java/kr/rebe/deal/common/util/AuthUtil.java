package kr.rebe.deal.common.util;

import kr.rebe.deal.common.exception.CustomException;
import kr.rebe.deal.common.exception.ErrorCode;
import kr.rebe.deal.dto.AuthDto;
import kr.rebe.deal.enums.LogInTypeEnum;
import kr.rebe.deal.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 인증 정보 Util
 */
@Slf4j
@RequiredArgsConstructor
public class AuthUtil {

    /**
     * 인증 정보 조회
     */
    public static AuthDto getAuth() {
        Cookie cookie = CookieUtil.getCookie(getRequest(), "sessionAuth");
        if (cookie == null) {
            return null;
        }
        String value = cookie.getValue();
        AuthDto authDto = getAuthService().sessionMember(value);
        return authDto;
    }

    /**
     * 로그인 여부 확인
     * */
    private static void isLoggedIn(AuthDto auth) {
        if (auth == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    }

    /**
     * 관리자인지 확인
     * */
    public static boolean isAdmin() {
        AuthDto auth = getAuth();
        isLoggedIn(auth);
        if (auth.getLoginType() == LogInTypeEnum.U) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ADMIN);
        }
        return true;
    }

    /**
     * 회원이면서 본인인지 확인
     * */
    public static boolean isMember(Long memberSeq) {
        AuthDto auth = getAuth();
        isLoggedIn(auth);
        if (memberSeq != auth.getMemberSeq() || auth.getLoginType() != LogInTypeEnum.U) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ADMIN);
        }
        return true;
    }

    /**
     * 관리자 또는 본인인지 확인
     * */
    public static boolean isAdminOrMember(Long memberSeq) {
        AuthDto auth = getAuth();
        isLoggedIn(auth);
        if (memberSeq != auth.getMemberSeq() && auth.getLoginType() != LogInTypeEnum.A) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ADMIN_MEMBER);
        }
        return true;
    }

    /**
     * 현재 요청 조회
     * */
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 현재 응답 조회
     * */
    private static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getResponse();
    }

    /**
     * getBean AuthService
     * */
    private static AuthService getAuthService() {
        return (AuthService) ApplicationUtil.getApplicationContext()
                .getBean("authService");
    }
}
