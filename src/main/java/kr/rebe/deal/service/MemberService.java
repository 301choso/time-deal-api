package kr.rebe.deal.service;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.repository.MemberRepository;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member joinMember(MemberDto memberDto) {
        Member member = memberDto.toEntity();
        return memberRepository.save(member);
    }

    public List<MemberDto> getMemberList() {
        List<MemberDto> list = new ArrayList<>();
        memberRepository.findAll().forEach(m -> list.add(m.toDto()));
        return list;
    }

    public MemberDto getMember(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElse(null);
        return member.toDto();
    }
}
