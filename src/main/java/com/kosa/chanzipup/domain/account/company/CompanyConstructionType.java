package com.kosa.chanzipup.domain.account.company;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CompanyConstructionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")  // ConstructionType의 외래 키로 설정
    private ConstructionType constructionType; // 변경: Long 대신 ConstructionType

    public CompanyConstructionType(ConstructionType constructionType, Company company) {
        this.constructionType = constructionType;
        this.company = company;
    }
}
