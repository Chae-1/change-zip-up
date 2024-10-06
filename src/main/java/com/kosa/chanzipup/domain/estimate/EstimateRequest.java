package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class EstimateRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @Column(unique = true)
    private String identification;

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

    @Enumerated(EnumType.STRING)
    private EstimateRequestStatus status;

    @OneToMany(mappedBy = "estimateRequest", cascade = CascadeType.ALL)
    private List<Estimate> estimates = new ArrayList<>();

    @Builder
    public EstimateRequest(String identification, String schedule, String budget,
                           String address, String detailedAddress, LocalDate measureDate, int floor,
                           BuildingType buildingType, Member member, LocalDateTime regDate,
                           EstimateRequestStatus status, List<ConstructionType> constructionTypes) {
        this.identification = identification;
        this.schedule = schedule;
        this.budget = budget;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.measureDate = measureDate;
        this.floor = floor;
        this.buildingType = buildingType;
        this.member = member;
        this.regDate = regDate;
        this.status = status;
        this.constructionTypes.addAll(
                constructionTypes.stream()
                .map(type -> new EstimateConstructionType(type, this))
                .toList()
        );
    }

    public void addConstructionType(EstimateConstructionType estimateConstructionType) {
        constructionTypes.add(estimateConstructionType);
    }

    public List<String> getConstructionTypeNames() {
        return constructionTypes.stream()
                .map(EstimateConstructionType::getTypeName)
                .collect(Collectors.toList());
    }

    public String getFullAddress() {
        return String.format("%s %s", address, detailedAddress);
    }

    public void ongoing() {
        this.status = EstimateRequestStatus.ONGOING;
    }

    public void cancel() {
        // 1. estimates 중, accept인 estimate를 찾는다.
        Optional<Estimate> accepted = estimates.stream()
                .filter(estimate -> estimate.getEstimateStatus() == EstimateStatus.ACCEPTED)
                .findAny();

        accepted.ifPresentOrElse(estimate -> {
            estimate.cancel();
            this.status = EstimateRequestStatus.WAITING;
        } , () -> {
            throw new IllegalArgumentException("estimate가 존재하지 않습니다.");
        });
    }

    public void complete() {
        Optional<Estimate> accepted = estimates.stream()
                .filter(estimate -> estimate.getEstimateStatus() == EstimateStatus.ACCEPTED)
                .findAny();

        accepted.ifPresentOrElse(estimate -> {
            estimate.complete();
            this.status = EstimateRequestStatus.COMPLETE;
        }, () -> {
            this.status = EstimateRequestStatus.COMPLETE;
        });
    }

    public void writtenReview() {
        this.status = EstimateRequestStatus.WRITTENREVIEW;
    }
}
