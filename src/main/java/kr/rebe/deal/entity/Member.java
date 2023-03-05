package kr.rebe.deal.entity;

import kr.rebe.deal.dto.MemberDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    private String memberName;

    private String loginType;

    private String loginId;

    private String loginPwd;

    private String hpNo;

    private String email;

    private String leaveYn;

    private LocalDateTime joinDate;

    private LocalDateTime regDate;

    @Builder
    public Member(Long memberSeq, String memberName, String loginType, String loginId, String loginPwd, String hpNo, String email, String leaveYn, LocalDateTime joinDate, LocalDateTime regDate) {
        this.memberSeq = memberSeq;
        this.memberName = memberName;
        this.loginType = loginType;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.hpNo = hpNo;
        this.email = email;
        this.leaveYn = leaveYn;
        this.joinDate = joinDate;
        this.regDate = regDate;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .memberSeq(memberSeq)
                .memberName(memberName)
                .loginId(loginId)
                .loginType(loginType)
                .regDate(regDate)
                .build();
    }

}
