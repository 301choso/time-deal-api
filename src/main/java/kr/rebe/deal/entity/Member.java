package kr.rebe.deal.entity;

import kr.rebe.deal.dto.MemberDto;
import kr.rebe.deal.enums.LogInTypeEnum;
import kr.rebe.deal.enums.YnEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    private String memberName;

    @Enumerated(EnumType.STRING)
    private LogInTypeEnum loginType;

    private String loginId;

    private String loginPwd;

    private String hpNo;

    private String email;

    @Enumerated(EnumType.STRING)
    private YnEnum leaveYn = YnEnum.N;

    private LocalDateTime joinDate;

    private LocalDateTime regDate;

    @Builder
    public Member(Long memberSeq, String memberName, LogInTypeEnum loginType, String loginId, String loginPwd, String hpNo, String email, YnEnum leaveYn, LocalDateTime joinDate, LocalDateTime regDate) {
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
