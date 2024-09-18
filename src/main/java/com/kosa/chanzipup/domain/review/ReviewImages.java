package com.kosa.chanzipup.domain.review;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


    public ReviewImages(String imageUrl, Review review) {
        this.imageUrl = imageUrl;
        this.review = review;
    }


    public static ReviewImages of(Review review, String uploadFullPath) {
        return new ReviewImages(uploadFullPath, review);
    }
}
