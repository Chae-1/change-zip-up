package com.kosa.chanzipup.api.review.controller;

import com.kosa.chanzipup.api.review.controller.response.ReviewListResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewRegisterResponse;
import com.kosa.chanzipup.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 후기 등록
    // 예외처리까지 고려하지 않았어요..
    @PostMapping("/create")
    public ResponseEntity<?> createReview() {
//        ReviewRegisterResponse savedReviewResponse = reviewService.registerReview(request, member, company);
        return null;
    }

    // 후기 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ReviewListResponse>> getAllCompany() {
        List<ReviewListResponse> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
}
