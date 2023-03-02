package kr.rebe.deal.controller;

import kr.rebe.deal.service.AuthService;
import kr.rebe.deal.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 Controller
 * */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     * */
    @GetMapping
    public ResponseEntity login(@ModelAttribute("loginVO") LoginVO loginVO) {
        boolean result = authService.loginCheck(loginVO);
        return ResponseEntity.status(result == true ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(null);
    }
}
