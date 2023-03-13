package kr.rebe.deal.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    UNAUTHORIZED_MEMBER(HttpStatus.BAD_REQUEST, "아이디 비밀번호가 올바르지 않거나 회원이 아닙니다."),
    UNAUTHORIZED_ADMIN(HttpStatus.BAD_REQUEST, "관리자 권한이 없습니다."),
    UNAUTHORIZED_ADMIN_MEMBER(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    NOT_EXIST_PRODUCT(HttpStatus.NOT_FOUND, "제품이 존재하지 않습니다."),
    NOT_SALE_TIME(HttpStatus.NOT_FOUND, "구매 가능한 시간이 아닙니다."),
    OUT_OF_STOCK(HttpStatus.NOT_FOUND, "구매 가능한 수량이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
