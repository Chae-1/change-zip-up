package com.kosa.chanzipup.api.portfolio.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioDetailResponse {

    private Long id;

    private String title;

    @Lob
    private String content;

    private int floor;

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    private String buildingType;

    private List<String> services;

    private Long companyId;

    private String companyName;

    private String companyAddress;

    private String companyPhone;

    private String companyLogo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean canUpdate;


    public PortfolioDetailResponse(Portfolio portfolio, List<String> services, boolean canUpdate) {
        this.id = portfolio.getId();
        this.title = portfolio.getTitle();
        this.content = portfolio.getContent();
        this.floor = portfolio.getFloor();
        this.projectBudget = portfolio.getProjectBudget();
        this.projectLocation = portfolio.getProjectLocation();
        this.startDate = portfolio.getStartDate();
        this.endDate = portfolio.getEndDate();
        this.buildingType = portfolio.getBuildingType().getName();
        this.services = services;

        Company company = portfolio.getCompany();
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyAddress = company.getAddress();
        this.companyPhone = company.getPhoneNumber();
        this.companyLogo = company.getCompanyLogoUrl();
        this.createdAt = portfolio.getCreatedAt();
        this.updatedAt = portfolio.getUpdatedAt();

        this.canUpdate = canUpdate;
    }
}
