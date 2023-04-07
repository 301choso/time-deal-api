package kr.rebe.deal.common.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> CustomException(CustomException e) {
        return ErrorResponse.responseEntity(e.getErrorCode());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Error commonException400() {
        return new Error("올바르지 않은 인자값으로 호출하셨습니다.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Error accessDeniedException403() {
        return new Error("권한이 없는 기능을 호출하셨습니다.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Error runtimeException500(RuntimeException e) {
        log.error("Runtime Exception", e);
        return new Error("서버 오류가 발생하였습니다. 잠시 후 다시 시도해주세요. 이 현상이 지속되면 사이트 관리자에게 문의바랍니다.");
    }

    @Getter
    public class Error {
        private String msg;

        public Error(String msg) {
            this.msg = msg;
        }
    }
}
