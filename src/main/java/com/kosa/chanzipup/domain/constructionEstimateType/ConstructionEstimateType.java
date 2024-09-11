package com.kosa.chanzipup.domain.constructionEstimateType;

import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import com.kosa.chanzipup.domain.estimate.Estimate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConstructionEstimateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)  // ConstructionType의 외래 키로 설정
    private ConstructionType constructionType; // 변경: Long 대신 ConstructionType

    public ConstructionEstimateType(ConstructionType constructionType, Estimate estimate) {
        this.constructionType = constructionType;
        this.estimate = estimate;
    }
}
