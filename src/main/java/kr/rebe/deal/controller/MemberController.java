package kr.rebe.deal.controller;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.service.MemberService;
import kr.rebe.deal.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class MemberController {

    private final MemberService memberService;
    /**
     * 회원가입
     * */
    @PostMapping("/join")
    public ResponseEntity<Member> joinMember(@ModelAttribute("memberDto") @Valid MemberDto memberDto) {
        Member member = memberService.joinMember(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    /**
     * 회원 리스트 조회
     * */
    @GetMapping()
    public ResponseEntity<List<MemberDto>> getMemberList() {
        // 세션에서 권한 확인
        List<MemberDto> memberList = memberService.getMemberList();
        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }

    /**
     * 회원 단일 조회
     * */
    @GetMapping("/{memberSeq}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long memberSeq) { // 권한 확인
        MemberDto member = memberService.getMember(memberSeq);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    /**
     * 아이디 중복 확인
     * */
    @GetMapping("/idCheck")
    public ResponseEntity checkLoginId(@RequestParam("loginId") String loginId) {
        boolean result = memberService.checkLoginId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 회원 탈퇴
     * */
    @PatchMapping("/leave/{memberSeq}")
    public ResponseEntity leaveMember(@RequestParam("memberSeq") Long memberSeq) {
        boolean result = memberService.leaveMember(memberSeq);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
