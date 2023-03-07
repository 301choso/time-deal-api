package kr.rebe.deal.common.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 기본 응답 양식
 * */
public class CommonResponse {

    /**
     * isSuccess, headers, body 전부 있는 경우
     * */
    public static <T> ResponseEntity<T> createResponseEntity(boolean isSuccess, HttpHeaders headers, T body) {
        if (!isSuccess) {
            headers = null;
            body = null;
        }
        return ResponseEntity.status(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST).headers(headers).body(body);
    }

    /**
     * isSuccess, body만 있는 경우
     * */
    public static <T> ResponseEntity<T> createResponseEntity(boolean isSuccess, T body) {
        return createResponseEntity(isSuccess, null, body);
    }

    /**
     * isSuccess, headers만 있는 경우
     * */
    public static <T> ResponseEntity<T> createResponseEntity(boolean isSuccess, HttpHeaders headers) {
        return createResponseEntity(isSuccess, headers, null);
    }

    /**
     * isSuccess만 있는 경우
     * */
    public static <T> ResponseEntity<T> createResponseEntity(boolean isSuccess) {
        return createResponseEntity(isSuccess, null, null);
    }


}
