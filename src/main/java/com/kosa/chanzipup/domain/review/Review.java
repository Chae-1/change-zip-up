package com.kosa.chanzipup.domain.review;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // string -> clob, other -> binary -> blob
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime workStartDateTime;

    private LocalDateTime workEndDateTime;

    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImages> reviewImages;

//    @ManyToOne(fetch = FetchType.LAZY)    // EstimateRequest 엔티티 추후에 연결할 예정
//    @JoinColumn(name = "estimate_id")
//    private EstimateRequest estimateRequest;

    //    @ManyToOne(fetch = FetchType.LAZY)    // Estimate 엔티티 추후에 연결할 예정
//    @JoinColumn(name = "estimate_id")
//    private Estimate estimate;

//    EstimateRequest Estimate 엔티티에서 가져와야 할 것들인데 이것 또한 입력하는 것으로 가정한다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_type_id")
    private BuildingType buildingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_type_id")
    private ConstructionType constructionType;

    private Long totalPrice;

    private int floor;


    @Builder
    private Review(String title, String content, LocalDateTime regDate, LocalDateTime workStartDateTime, LocalDateTime workEndDateTime
            , double rating, Member member, Company company, List<ReviewImages> reviewImages
            , BuildingType buildingType, ConstructionType constructionType, Long totalPrice, int floor
    ) {
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.workStartDateTime = workStartDateTime;
        this.workEndDateTime = workEndDateTime;
        this.rating = rating;
        this.member = member;
        this.company = company;
        this.reviewImages = reviewImages;
        this.buildingType = buildingType;
        this.constructionType = constructionType;
        this.totalPrice = totalPrice;
        this.floor = floor;
    }

    public static Review ofNewReview(String title, String content, LocalDateTime regDate, LocalDateTime workStartDateTime, LocalDateTime workEndDateTime
            , double rating, Member member, Company company, List<ReviewImages> reviewImages
            , BuildingType buildingType, ConstructionType constructionType, Long totalPrice, int floor
    ) {
        return Review.builder()
                .title(title)
                .content(content)
                .regDate(regDate)
                .workStartDateTime(workStartDateTime)
                .workEndDateTime(workEndDateTime)
                .rating(rating)
                .member(member)
                .company(company)
                .reviewImages(reviewImages)
                .buildingType(buildingType)
                .constructionType(constructionType)
                .totalPrice(totalPrice)
                .floor(floor)
                .build();
    }
}
