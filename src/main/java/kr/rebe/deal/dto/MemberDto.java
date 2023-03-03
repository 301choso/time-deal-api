package kr.rebe.deal.dto;

import kr.rebe.deal.entity.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long memberSeq;

    private String memberName;

    private String loginType;

    @NotEmpty
    private String loginId;

    @NotEmpty
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
