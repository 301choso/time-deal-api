package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.enums.YnEnum;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * */
    public Member joinMember(MemberDto memberDto) {
        String hashpw = BCrypt.hashpw(memberDto.getLoginPwd(), BCrypt.gensalt());
        memberDto.setLoginPwd(hashpw);
        Member member = memberDto.toEntity();

        Member result = null;
        if (checkLoginId(memberDto.getLoginId())) {
            result = memberRepository.save(member);
        }
        return result;
    }

    /**
     * 회원 리스트 조회
     * */
    public List<MemberDto> getMemberList() {
        List<MemberDto> list = new ArrayList<>();
        memberRepository.findAll().forEach(m -> list.add(m.toDto()));
        return list;
    }

    /**
     * 회원 단일 조회
     * */
    public MemberDto getMember(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElse(null);
        return member.toDto();
    }

    /**
     * 아이디 중복 확인
     * */
    public boolean checkLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId) == null ? true : false;
    }

    /**
     * 회원 탈퇴
     * */
    @Transactional
    public boolean leaveMember(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElse(null);
        if (member == null) {
            return false;
        }
        return memberRepository.updateLeaveYn(memberSeq, YnEnum.Y, LocalDateTime.now()) > 0 ? true : false;
    }
}
