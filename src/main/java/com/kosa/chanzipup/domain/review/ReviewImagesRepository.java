package com.kosa.chanzipup.domain.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewImagesRepository extends JpaRepository<ReviewImages, Long> {

    @Query("select images from ReviewImages images join fetch images.review r where r.id in :reviewIds")
    List<ReviewImages> findAllByReviewIds(List<Long> reviewIds);

    @Query("delete from ReviewImages i where i.review.id = :reviewId")
    @Modifying
    void deleteByReviewId(@Param("reviewId") Long reviewId);
}
