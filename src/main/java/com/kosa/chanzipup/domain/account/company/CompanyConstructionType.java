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
    @JoinColumn(name = "type_id")
    private ConstructionType constructionType;

    public CompanyConstructionType(ConstructionType constructionType, Company company) {
        this.constructionType = constructionType;
        this.company = company;
    }
}
