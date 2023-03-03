package kr.rebe.deal.service;

import kr.rebe.deal.dto.LoginDto;
import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    LoginDto loginDto = new LoginDto();

    MemberDto memberDto;
    Member mem;

    @BeforeEach
    void beforeTest() {
        // 회원추가
        memberDto = MemberDto.builder()
                .loginId("member1")
                .loginPwd("1234")
                .build();
        mem = memberService.joinMember(memberDto);
    }

    @AfterEach
    void afterTest() {
        // 회원 삭제
        sessionRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 값 체크_성공")
    void test1() {
        loginDto.setLoginId("member1");
        loginDto.setLoginPwd("1234");
        boolean b = authService.logIn(loginDto);
        Assertions.assertEquals(true, b);
    }

    @Test
    @DisplayName("로그인 값 체크_실패")
    void test2() {
        loginDto.setLoginId("member1");
        loginDto.setLoginPwd("12345");
        boolean b = authService.logIn(loginDto);
        Assertions.assertEquals(false, b);
    }

    @Test
    @DisplayName("세션 생성 확인")
    void test3() {
        Session session = authService.addSession(mem);
        Assertions.assertNotNull(session);
    }
}