package com.kosa.chanzipup.domain.review;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private LocalDateTime regDate;

    private LocalDate workStartDate;

    private LocalDate workEndDate;

    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImages> reviewImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_type_id")
    private BuildingType buildingType;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewConstructionType> reviewConstructionTypes = new ArrayList<>();

    private Long totalPrice;

    private int floor;

    @Builder
    private Review(String title, String content, LocalDateTime regDate, LocalDate workStartDate, LocalDate workEndDate,
                   int rating, Member member, Company company,
                   BuildingType buildingType, List<ConstructionType> constructionTypes, Long totalPrice, int floor
    ) {
        constructionTypes = (constructionTypes == null) ? Collections.emptyList() : constructionTypes;

        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.rating = rating;
        this.reviewConstructionTypes.addAll(constructionTypes.stream()
                .map((type) -> new ReviewConstructionType(this, type))
                .toList());
        this.member = member;
        this.company = company;
        this.buildingType = buildingType;
        this.totalPrice = totalPrice;
        this.floor = floor;
    }

    public static Review ofNewReview(String title, LocalDateTime regDate, LocalDate workStartDate, LocalDate workEndDate,
                                     int rating, Member member, Company company, BuildingType buildingType,
                                     List<ConstructionType> constructionTypes, Long totalPrice, int floor) {

        return Review.builder()
                .title(title)
                .regDate(regDate)
                .workStartDate(workStartDate)
                .workEndDate(workEndDate)
                .rating(rating)
                .member(member)
                .company(company)
                .buildingType(buildingType)
                .constructionTypes(constructionTypes)
                .totalPrice(totalPrice)
                .floor(floor)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
