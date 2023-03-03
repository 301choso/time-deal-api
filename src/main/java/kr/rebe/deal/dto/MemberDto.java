package kr.rebe.deal.dto;

import kr.rebe.deal.entity.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long memberSeq;

    private String memberName;

    private String loginType;

    @NotEmpty(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어, 숫자만 입력해주세요.")
    @Size(min = 4, max = 12, message = "4 ~ 12 자리로 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 12, message = "8 ~ 12 자리로 입력해주세요.")
    private String loginPwd;

    private String hpNo;

    private String email;

    private String leaveYn;

    private LocalDateTime joinDate;

    @Builder
    public MemberDto(Long memberSeq, String memberName, String loginType, String loginId, String loginPwd, String hpNo, String email, String leaveYn, LocalDateTime joinDate) {
        this.memberSeq = memberSeq;
        this.memberName = memberName;
        this.loginType = loginType;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.hpNo = hpNo;
        this.email = email;
        this.leaveYn = leaveYn;
        this.joinDate = joinDate;
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .loginType("Y")
                .loginId(loginId)
                .loginPwd(loginPwd)
                .hpNo(hpNo)
                .email(email)
                .leaveYn("N")
                .joinDate(LocalDateTime.now())
                .build();
    }
}
