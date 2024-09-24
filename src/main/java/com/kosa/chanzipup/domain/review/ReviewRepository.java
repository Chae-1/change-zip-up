package com.kosa.chanzipup.domain.review;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r")
    @EntityGraph(attributePaths = {"member", "company", "buildingType"}) // estimate 주석처리되어 있어서 전체 목록 조회하는데 문제가 생겨서 일단 estimate를 뺐어요
    List<Review> findAllWithAll();

    @Query("select r from Review r join fetch r.member m where r.id = :reviewId")
    Optional<Review> findByIdWithMember(@Param("reviewId") Long reviewId);

    @Query("select r from Review r left join r.company c where c.id = :companyId")
    List<Review> findByCompanyId(@Param("companyId") Long companyId);

    @Query("select r from Review r left join fetch r.member m"
            +" left join fetch r.reviewImages images"
            +" where r.id = :reviewId and m.email = :userEmail")
    Optional<Review> findByIdAndUserEmail(@Param("reviewId") Long reviewId, @Param("userEmail") String userEmail);
}
