package com.kosa.chanzipup.domain.review;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r")
    @EntityGraph(attributePaths = {"member", "company"}) // estimate 주석처리되어 있어서 전체 목록 조회하는데 문제가 생겨서 일단 estimate를 뺐어요
    List<Review> findAllWithAll();
}
