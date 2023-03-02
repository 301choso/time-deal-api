package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.entity.Session;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.repository.SessionRepository;
import kr.rebe.deal.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;

    /**
     * 로그인 값 체크
     * @param loginVO
     * @return boolean
     * */
    @Transactional
    public boolean loginCheck(LoginVO loginVO) {
        Member member = memberRepository.findByLoginIdAndLoginPwd(loginVO.getLoginId(), loginVO.getLoginPwd()).orElse(null);
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
}
