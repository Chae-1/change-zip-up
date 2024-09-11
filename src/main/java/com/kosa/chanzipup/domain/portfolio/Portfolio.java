package com.kosa.chanzipup.domain.portfolio;

import com.kosa.chanzipup.domain.account.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private int projectArea;

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(name = "company_id") // company_id 필드 추가
    private Long accountId;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioImage> images;

    @Builder
    private Portfolio(String title, String content, int projectArea, int projectBudget,
                      String projectLocation, LocalDate startDate, LocalDate endDate,
                      long accountId) {
        this.title = title;
        this.content = content;
        this.projectArea = projectArea;
        this.projectBudget = projectBudget;
        this.projectLocation = projectLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountId = accountId;
    }

    public static Portfolio ofNewPortfolio(String title, String content,
                                           int projectArea, int projectBudget,
                                           String projectLocation, LocalDate startDate,
                                           LocalDate endDate, Long accountId) {
        return Portfolio.builder()
                .title(title)
                .content(content)
                .projectArea(projectArea)
                .projectBudget(projectBudget)
                .projectLocation(projectLocation)
                .startDate(startDate)
                .endDate(endDate)
                .accountId(accountId)
                .build();
    }

}
