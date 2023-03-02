package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member joinMember(MemberVO memberVO) {
        Member member = Member.builder()
                .memberName(memberVO.getMemberName())
                .loginType("Y")
                .loginId(memberVO.getLoginId())
                .loginPwd(memberVO.getLoginPwd())
                .HpNo(memberVO.getHpNo())
                .email(memberVO.getEmail())
                .leaveYn("N")
                .joinDate(LocalDateTime.now())
                .build();
        return memberRepository.save(member);
    }
}
