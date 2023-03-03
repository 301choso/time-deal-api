package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import kr.rebe.deal.dto.LoginDto;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;

    /**
     * 로그인 값 체크
     * @param loginDto
     * @return boolean
     * */
    @Transactional
    public boolean logIn(LoginDto loginDto) {
        Member member = memberRepository.findByLoginIdAndLoginPwd(loginDto.getLoginId(), loginDto.getLoginPwd()).orElse(null);
        if (member == null) {
            return false;
        }

        addSession(member);
        return true;
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
