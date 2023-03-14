package kr.rebe.deal.service;

import kr.rebe.deal.dto.AuthDto;
import kr.rebe.deal.dto.LoginDto;
import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

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

    @Mock
    HttpServletResponse response;

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
        boolean b = authService.logIn(loginDto, response);
        Assertions.assertEquals(true, b);
    }

    @Test
    @DisplayName("로그인 값 체크_실패")
    void test2() {
        loginDto.setLoginId("member");
        loginDto.setLoginPwd("12345");
        Assertions.assertThrows(RuntimeException.class, () -> {
            authService.logIn(loginDto, response);
        });
    }

    @Test
    @DisplayName("세션 생성 확인")
    void test3() {
        Session session = authService.addSession(mem);
        Assertions.assertNotNull(session);
    }

    @Test
    @DisplayName("세션 삭제 확인")
    void test4() {
        Session session = authService.addSession(mem);
        authService.removeSession(mem.getMemberSeq());
        Session sessionInfo = sessionRepository.findById(mem.getMemberSeq()).orElse(null);
        Assertions.assertNull(sessionInfo);
    }

    @Test
    @DisplayName("세션 값이 맞으면 member 값을 준다.")
    @Transactional
    void test5() {
        Session session = authService.addSession(mem);
        AuthDto authDto = authService.sessionMember(session.getAccessToken());
        Assertions.assertEquals(mem.getMemberSeq(), authDto.getMemberSeq());
    }
}