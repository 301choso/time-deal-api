package kr.rebe.deal.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    UNAUTHORIZED_MEMBER(HttpStatus.BAD_REQUEST, "아이디 비밀번호가 올바르지 않거나 회원이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
