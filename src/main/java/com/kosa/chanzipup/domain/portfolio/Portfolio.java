package com.kosa.chanzipup.domain.portfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private String projectType;

    private int projectArea;

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    private Portfolio(String title, String content, String projectType, int projectArea,
                      int projectBudget, String projectLocation, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.projectType = projectType;
        this.projectArea = projectArea;
        this.projectBudget = projectBudget;
        this.projectLocation = projectLocation;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Portfolio ofNewPortfolio(String title, String content,
                                           String projectType, int projectArea,
                                           int projectBudget, String projectLocation,
                                           LocalDate startDate, LocalDate endDate) {
        return Portfolio.builder()
                .title(title)
                .content(content)
                .projectType(projectType)
                .projectArea(projectArea)
                .projectBudget(projectBudget)
                .projectLocation(projectLocation)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
