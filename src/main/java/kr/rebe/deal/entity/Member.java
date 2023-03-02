package kr.rebe.deal.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@SuperBuilder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    private String memberName;

    private String loginType;

    private String loginId;

    private String loginPwd;

    private String HpNo;

    private String email;

    private String leaveYn;

    private LocalDateTime joinDate;

}
