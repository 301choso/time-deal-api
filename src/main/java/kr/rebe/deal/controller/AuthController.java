package kr.rebe.deal.controller;

import kr.rebe.deal.service.AuthService;
import kr.rebe.deal.dto.LoginDto;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/logIn")
    public ResponseEntity logIn(@ModelAttribute("loginDto") LoginDto loginDto) {
        boolean result = authService.logIn(loginDto);
        return ResponseEntity.status(result == true ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * 로그아웃
     * */
    @PostMapping("/logOut")
    public ResponseEntity logOut(@ModelAttribute("memberDto") MemberDto memberDto) {
        authService.removeSession(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
