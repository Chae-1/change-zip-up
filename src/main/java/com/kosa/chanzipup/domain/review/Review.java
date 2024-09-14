package com.kosa.chanzipup.domain.review;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // string -> clob, other -> binary -> blob
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime workStartDateTime;

    private LocalDateTime workEndDateTime;

    private double rating;

    private int projectArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_request_id")
    private EstimateRequest estimate;
}
