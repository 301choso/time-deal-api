package kr.rebe.deal.service;

import kr.rebe.deal.common.exception.CustomException;
import kr.rebe.deal.common.exception.ErrorCode;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.enums.YnEnum;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import kr.rebe.deal.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;

    /**
     *  값 체크
     * */
    @Transactional
    public boolean logIn(LoginDto loginDto, HttpServletResponse response) {
        Member byLoginId = memberRepository.findByLoginId(loginDto.getLoginId());
        if (byLoginId == null || byLoginId.getLeaveYn() == YnEnum.Y ||
            !BCrypt.checkpw(loginDto.getLoginPwd(), byLoginId.getLoginPwd())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
        String sessionAuth = setSession(byLoginId);
        setCookie(response, sessionAuth);
        return true;
    }

    /**
     *  쿠키 설정
     * */
    private static void setCookie(HttpServletResponse response, String sessionAuth) {
        Cookie cookie = new Cookie("sessionAuth", sessionAuth);
        response.addCookie(cookie);
    }

    /**
     *  세션 설정
     * */
    private String setSession(Member byLoginId) {
        Session session = addSession(byLoginId);
        return session.getAccessToken();
    }

    /**
     * 세션 생성
     * */
    protected Session addSession(Member member) {
        Session session = Session.builder()
                .member(member)
                .accessToken(UUID.randomUUID().toString())
                .regDate(LocalDateTime.now())
                .build();
        return sessionRepository.save(session);
    }

    /**
     * 세션 삭제
     * */
    @Transactional
    public void removeSession(Long memberSeq) {
        Member member = Member.builder().memberSeq(memberSeq).build();
        sessionRepository.deleteAllByMember(member);
    }

    /**
     * 세션 값이 맞으면 로그인 값을 준다.
     * */
    public Member sessionMember(String accessToken) {
        Session session = sessionRepository.findByAccessToken(accessToken);
        return session.getMember();
    }

}
