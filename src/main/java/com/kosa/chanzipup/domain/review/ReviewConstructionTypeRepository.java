package com.kosa.chanzipup.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewConstructionTypeRepository extends JpaRepository<ReviewConstructionType, Long> {
    @Query("select type from ReviewConstructionType type join fetch type.constructionType ct join fetch type.review r where r.id in :reviewIds")
    List<ReviewConstructionType> findAllByReviewIds(List<Long> reviewIds);
}
