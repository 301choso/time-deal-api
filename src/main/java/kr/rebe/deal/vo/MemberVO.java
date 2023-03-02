package kr.rebe.deal.vo;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MemberVO {

    private Long memberSeq;

    private String memberName;

    private String loginType;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String loginPwd;

    private String HpNo;

    private String email;

    private String leaveYn;

    private LocalDateTime joinDate;
}
