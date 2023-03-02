package kr.rebe.deal.entity;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@SuperBuilder
public class Session {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionSeq;

    private Long memberSeq;

    private String accessToken;

    private LocalDateTime regDate;

}
