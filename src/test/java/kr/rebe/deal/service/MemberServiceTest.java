package kr.rebe.deal.service;

import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void joinMember() {
        MemberDto memberDto = MemberDto.builder()
                .loginId("member")
                .loginPwd("1111")
                .build();
        Member member = memberService.joinMember(memberDto);
        Optional<Member> byId = null;
        if (member != null) {
            byId = memberRepository.findById(member.getMemberSeq());
        }
        Assertions.assertNotNull(byId);
        memberRepository.deleteById(byId.orElseThrow().getMemberSeq());
    }

    @Test
    @DisplayName("회원 목록 조회")
    void getMemberList() {
        Member data = Member.builder()
                .loginId("member")
                .loginPwd("1111")
                .build();
        Member save = memberRepository.save(data);
        List<MemberDto> memberList = memberService.getMemberList();
        Assertions.assertTrue(memberList.size() > 0);
        memberRepository.deleteById(save.getMemberSeq());
    }

    @Test
    @DisplayName("회원 단일 조회")
    void getMember() {
        Member data = Member.builder()
                .loginId("member")
                .loginPwd("1111")
                .build();
        Member save = memberRepository.save(data);
        Member seq = memberRepository.findAll().get(0);
        MemberDto member = memberService.getMember(seq.getMemberSeq());
        Assertions.assertNotNull(member);
        memberRepository.deleteById(save.getMemberSeq());
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void checkLoginId() {
        MemberDto memberDto1 = MemberDto.builder()
                .loginId("member1")
                .loginPwd("1111")
                .build();
        Member member = memberService.joinMember(memberDto1);
        boolean result = memberService.checkLoginId("member1");
        Assertions.assertFalse(result);
        memberRepository.deleteById(member.getMemberSeq());
    }

    @Test
    @DisplayName("회원 탈퇴 확인")
    void leaveMember() {
        MemberDto memberDto1 = MemberDto.builder()
                .loginId("member1")
                .loginPwd("1111")
                .build();
        Member member = memberService.joinMember(memberDto1);
        boolean result = memberService.leaveMember(member.getMemberSeq());
        Assertions.assertTrue(result);
        memberRepository.deleteById(member.getMemberSeq());
    }
}