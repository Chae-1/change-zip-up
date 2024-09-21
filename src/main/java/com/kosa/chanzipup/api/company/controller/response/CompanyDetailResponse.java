package com.kosa.chanzipup.api.company.controller.response;

import com.kosa.chanzipup.api.review.controller.response.ReviewDetail;
import com.kosa.chanzipup.api.review.controller.response.ReviewListResponse;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.review.Review;
import java.time.LocalDateTime;
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
        this.companyName = findCompany.getName();
        this.owner = findCompany.getOwner();
        this.companyLogoUrl = findCompany.getCompanyLogoUrl();
        this.phoneNumber = findCompany.getPhoneNumber();
        this.companyDesc = findCompany.getCompanyDesc();
        this.address = findCompany.getAddress();
        this.rating = findCompany.getRating();
        this.reviews = Page.ofDefault(
                reviewList.stream()
                .map(SimpleReviewResponse::new)
                .toList());
        this.responses = Page.ofDefault(portfolios
                .stream()
                .map(SimplePortfolioResponse::new)
                .toList());
    }


    @Getter
    static class SimpleReviewResponse {
        private Long reviewId;
        private String title;
        private String reviewImageUrl;
        private LocalDateTime regDate;

        public SimpleReviewResponse(Review review) {
            this.reviewId = review.getId();
            this.title = review.getTitle();
            this.reviewImageUrl = review
                    .getReviewImages()
                    .get(0)
                    .getImageUrl();
            this.regDate = review.getRegDate();
        }
    }


    @Getter
    static class SimplePortfolioResponse {
        private String title;
        private String buildingTypeName;
        private int floor;
        private String budget;
        private String location;
        private LocalDate startDate;
        private LocalDate endDate;

        public SimplePortfolioResponse(Portfolio portfolio) {
            this.title = portfolio.getTitle();
            this.buildingTypeName = portfolio.getBuildingType().getName();
            this.floor = portfolio.getProjectArea();
            this.budget = String.format("%d 만원", portfolio.getProjectBudget());
            this.location = portfolio.getProjectLocation();
            this.startDate = portfolio.getStartDate();
            this.endDate = portfolio.getEndDate();
        }
    }
}