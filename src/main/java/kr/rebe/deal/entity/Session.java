package kr.rebe.deal.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionSeq;

    private Long memberSeq;

    private String accessToken;

    private LocalDateTime regDate;

}
