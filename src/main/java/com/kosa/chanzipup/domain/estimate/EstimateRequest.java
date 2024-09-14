package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
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
@Table(name = "estimate_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate regDate;

    @Column(nullable = false, unique = true)
    private String identification;

    @Column(nullable = false)
    private String estimateStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "estimateRequest")
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

    @Column
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_type_id")
    private BuildingType buildingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public EstimateRequest(String identification, String estimateStatus, String schedule, String budget,
                    String address, String detailedAddress, LocalDate measureDate, int floor,
                    BuildingType buildingType, Member member, LocalDate regDate) {
        this.identification = identification;
        this.estimateStatus = estimateStatus;
        this.schedule = schedule;
        this.budget = budget;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.measureDate = measureDate;
        this.floor = floor;
        this.buildingType = buildingType;
        this.member = member;
        this.regDate = regDate;
    }

    public void addConstructionType(EstimateConstructionType estimateConstructionType) {
        constructionTypes.add(estimateConstructionType);
    }
}