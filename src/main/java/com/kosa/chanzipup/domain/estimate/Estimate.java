package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "estimate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String buildingType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "estimate")
    private List<EstimateConstructionType> constructionTypes = new ArrayList<>();

    @Column(nullable = false)
    private String schedule;

    @Column(nullable = false)
    private String budget;

    @Column(nullable = false)
    private String address;

    @Column
    private String detailedAddress;

    @Column
    private LocalDate measureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Estimate(String buildingType, String schedule, String budget, String address, String detailedAddress, LocalDate measureDate) {
        this.buildingType = buildingType;
        this.schedule = schedule;
        this.budget = budget;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.measureDate = measureDate;
    }

    public void addConstructionType(EstimateConstructionType estimateConstructionType) {
        constructionTypes.add(estimateConstructionType);
    }
}
