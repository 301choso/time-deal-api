package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import kr.rebe.deal.dto.LoginDto;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public boolean logIn(LoginDto loginDto, HttpServletRequest request) {
        Member byLoginId = memberRepository.findByLoginId(loginDto.getLoginId());
        if (byLoginId != null && byLoginId.getLeaveYn().equals("N")) {
            if (BCrypt.checkpw(loginDto.getLoginPwd(), byLoginId.getLoginPwd())) {
                setSession(request, byLoginId);
                return true;
            }
        }
        return false;
    }

    /**
     *  세션 설정
     * */
    private void setSession(HttpServletRequest request, Member byLoginId) {
        Session session = addSession(byLoginId);
        request.setAttribute("sessionAuth", session.getAccessToken());
    }

    /**
     * 세션 생성
     * */
    protected Session addSession(Member member) {
        Session session = Session.builder()
                .memberSeq(member.getMemberSeq())
                .accessToken(UUID.randomUUID().toString())
                .regDate(LocalDateTime.now())
                .build();
        return sessionRepository.save(session);
    }

    /**
     * 세션 삭제
     * */
    public void removeSession(MemberDto memberDto) {
        sessionRepository.deleteAllByMemberSeq(memberDto.getMemberSeq());
    }
}
