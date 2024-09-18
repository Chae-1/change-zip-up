package com.kosa.chanzipup.domain.review;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewConstructionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_type_id")
    private ConstructionType constructionType;


    public ReviewConstructionType(Review review, ConstructionType constructionType) {
        this.review = review;
        this.constructionType = constructionType;
    }

}
