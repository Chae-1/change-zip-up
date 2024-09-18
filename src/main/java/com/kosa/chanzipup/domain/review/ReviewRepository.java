package com.kosa.chanzipup.domain.review;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r")
    @EntityGraph(attributePaths = {"estimate", "member", "company"})
    List<Review> findAllWithAll();

    @Query("select r from Review r join fetch r.member m where r.id = :reviewId")
    Optional<Review> findByIdWithMember(@Param("reviewId") Long reviewId);
}
