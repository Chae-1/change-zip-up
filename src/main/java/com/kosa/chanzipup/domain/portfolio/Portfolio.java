package com.kosa.chanzipup.domain.portfolio;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioUpdateRequest;
import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private int projectArea;

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_type_id")
    private BuildingType buildingType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<PortfolioConstructionType> constructionTypes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "portfolio")
    private List<PortfolioImage> portfolioImages = new ArrayList<>();

    @Builder
    private Portfolio(String title, String content, int projectArea, int projectBudget,
                      String projectLocation, LocalDate startDate, LocalDate endDate,
                      Account account, BuildingType buildingType) {
        this.title = title;
        this.content = content;
        this.projectArea = projectArea;
        this.projectBudget = projectBudget;
        this.projectLocation = projectLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.account = account;
        this.buildingType = buildingType;
    }

    public static Portfolio ofNewPortfolio(String title, String content,
                                           int projectArea, int projectBudget,
                                           String projectLocation, LocalDate startDate,
                                           LocalDate endDate, Account account,
                                           BuildingType buildingType) {
        return Portfolio.builder()
                .title(title)
                .content(content)
                .projectArea(projectArea)
                .projectBudget(projectBudget)
                .projectLocation(projectLocation)
                .startDate(startDate)
                .endDate(endDate)
                .account(account)
                .buildingType(buildingType)
                .build();
    }

    public void addConstructionType(PortfolioConstructionType constructionType) {
        constructionTypes.add(constructionType);
    }

    public void update(PortfolioUpdateRequest portfolioRequest) {
        this.content = portfolioRequest.getContent();
    }
}
