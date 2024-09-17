package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.company.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "estimate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstimateStatus estimateStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_request_id", nullable = false)
    private EstimateRequest estimateRequest;

    @Builder
    public Estimate(Long totalPrice, EstimateStatus estimateStatus, Company company, EstimateRequest estimateRequest) {
        this.totalPrice = totalPrice;
        this.estimateStatus = estimateStatus;
        this.company = company;
        this.estimateRequest = estimateRequest;
    }

    public static Estimate of(Long totalPrice, EstimateStatus estimateStatus, Company company, EstimateRequest estimateRequest) {
        return new Estimate(totalPrice, estimateStatus, company, estimateRequest);
    }

    public static Estimate waiting(Company company, EstimateRequest request) {
        return Estimate.builder()
                .company(company)
                .estimateStatus(EstimateStatus.WAITING)
                .totalPrice(0L)
                .estimateRequest(request)
                .build();
    }

    public void updateEstimateStatus(EstimateStatus newStatus) {
        this.estimateStatus = newStatus;
    }
}
