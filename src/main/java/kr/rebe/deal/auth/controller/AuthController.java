package kr.rebe.deal.auth.controller;

import kr.rebe.deal.common.response.CommonResponse;
import kr.rebe.deal.auth.AuthService;
import kr.rebe.deal.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 인증 Controller
 * */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends CommonResponse {

    private final AuthService authService;

    /**
     * 로그인
     * */
    @GetMapping("/logIn")
    public ResponseEntity logIn(@ModelAttribute("loginDto") @Valid LoginDto loginDto, HttpServletResponse response) {
        boolean isSuccess = authService.logIn(loginDto, response);
        return createResponseEntity(isSuccess);
    }

    /**
     * 로그아웃
     * */
    @DeleteMapping("/logOut/{memberSeq}")
    public ResponseEntity logOut(@PathVariable("memberSeq") Long memberSeq) {
        authService.removeSession(memberSeq);
        return createResponseEntity(true);
    }
}
