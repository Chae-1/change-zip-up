package com.kosa.chanzipup.api.company.controller.response;

import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioImage;
import com.kosa.chanzipup.domain.review.Review;
import java.time.LocalDateTime;

import com.kosa.chanzipup.domain.review.ReviewImages;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyDetailResponse {
    private Long companyId;

    private String companyName;

    private String companyNumber;

    private String owner;

    private String address;

    private String companyLogoUrl;

    private String phoneNumber;

    private String companyDesc;

    private LocalDate publishDate;

    private double rating;

    private List<String> services;

    private Page<List<SimpleReviewResponse>> reviews;

    private Page<List<SimplePortfolioResponse>> responses;

    public CompanyDetailResponse(List<Review> reviewList,
                                 List<Portfolio> portfolios,
                                 Company findCompany) {
        this.companyId = findCompany.getId();
        this.companyName = findCompany.getCompanyName();
        this.owner = findCompany.getOwner();

        this.companyLogoUrl = findCompany.getCompanyLogoUrl();
        this.phoneNumber = findCompany.getPhoneNumber();
        this.companyDesc = findCompany.getCompanyDesc();
        this.address = findCompany.getAddress();
        this.rating = findCompany.getRating();
        this.publishDate = findCompany.getPublishDate();
        this.companyNumber = findCompany.getCompanyNumber();


        this.reviews = Page.ofDefault(
                reviewList.stream()
                .map(SimpleReviewResponse::new)
                .toList());
        this.responses = Page.ofDefault(portfolios
                .stream()
                .map(SimplePortfolioResponse::new)
                .toList());

        this.services = findCompany.getConstructionTypes()
                .stream()
                .map(type -> type.getConstructionType().getName())
                .toList();
    }


    @Getter
    static class SimpleReviewResponse {
        private Long reviewId;
        private String title;
        private String reviewImageUrl;
        private LocalDateTime regDate;

        private int floor;
        private String buildingTypeName;
        private LocalDate startDate;
        private LocalDate endDate;

        public SimpleReviewResponse(Review review) {
            this.reviewId = review.getId();
            this.title = review.getTitle();

            List<ReviewImages> reviewImages = review.getReviewImages();
            this.reviewImageUrl = reviewImages.isEmpty() ? "" : reviewImages.get(0).getImageUrl();
            this.regDate = review.getRegDate();

            this.floor = review.getFloor();
            this.buildingTypeName = review.getBuildingType().getName();
            this.startDate = review.getWorkStartDate();
            this.endDate = review.getWorkEndDate();
        }
    }


    @Getter
    static class SimplePortfolioResponse {
        private Long portfolioId;
        private String title;
        private String buildingTypeName;
        private int floor;
        private String budget;
        private String location;
        private LocalDate startDate;
        private LocalDate endDate;
        private String imageUrl;

        public SimplePortfolioResponse(Portfolio portfolio) {
            this.portfolioId = portfolio.getId();
            this.title = portfolio.getTitle();
            this.buildingTypeName = portfolio.getBuildingType().getName();
            this.floor = portfolio.getFloor();
            this.budget = String.format("%d 만원", portfolio.getProjectBudget());
            this.location = portfolio.getProjectLocation();
            this.startDate = portfolio.getStartDate();
            this.endDate = portfolio.getEndDate();

            List<PortfolioImage> portfolioImages = portfolio.getPortfolioImages();
            this.imageUrl = portfolioImages.isEmpty() ? "" : portfolioImages.get(0).getImageUrl();
        }
    }
}