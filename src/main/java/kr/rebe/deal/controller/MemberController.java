package kr.rebe.deal.controller;

import kr.rebe.deal.entity.Member;
import kr.rebe.deal.service.MemberService;
import kr.rebe.deal.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
     * @param memberVO
     * @return
     * */
    @PostMapping("/join")
    public ResponseEntity<Member> join(@ModelAttribute("memberVO") @Valid MemberVO memberVO) {
        Member member = memberService.joinMember(memberVO);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }
}
