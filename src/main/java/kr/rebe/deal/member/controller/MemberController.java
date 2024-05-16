package kr.rebe.deal.member.controller;

import io.micrometer.core.annotation.Timed;
import kr.rebe.deal.common.aop.AdminCheck;
import kr.rebe.deal.common.aop.MemberOrAdminCheck;
import kr.rebe.deal.common.response.CommonResponse;
import kr.rebe.deal.entity.Member;
import kr.rebe.deal.member.service.MemberService;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 회원 Controller
 * */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController extends CommonResponse{

    private final MemberService memberService;
    /**
     * 회원가입
     * */
    @PostMapping("/join")
    public ResponseEntity<Member> joinMember(@ModelAttribute("memberDto") @Valid MemberDto memberDto) {
        Member member = memberService.joinMember(memberDto);
        return createResponseEntity(true, null, member);
    }

    /**
     * 회원 리스트 조회
     * */
    @GetMapping()
    @AdminCheck
    public ResponseEntity<List<MemberDto>> getMemberList() {
        List<MemberDto> memberList = memberService.getMemberList();
        return createResponseEntity(true, null, memberList);
    }

    /**
     * 회원 단일 조회
     * */
    @GetMapping("/{memberSeq}")
    @MemberOrAdminCheck
    public ResponseEntity<MemberDto> getMember(@PathVariable Long memberSeq) {
        MemberDto member = memberService.getMember(memberSeq);
        return createResponseEntity(true, null, member);
    }

    /**
     * 아이디 중복 확인
     * */
    @GetMapping("/idCheck")
    @Timed(value = "idCheck", longTask = true)
    public ResponseEntity checkLoginId(@RequestParam("loginId") String loginId) {
        boolean isSuccess = memberService.checkLoginId(loginId);
        return createResponseEntity(isSuccess);
    }

    /**
     * 회원 탈퇴
     * */
    @PatchMapping("/leave/{memberSeq}")
    @MemberOrAdminCheck
    public ResponseEntity leaveMember(@RequestParam("memberSeq") Long memberSeq) {
        boolean isSuccess = memberService.leaveMember(memberSeq);
        return createResponseEntity(isSuccess);
    }
}
