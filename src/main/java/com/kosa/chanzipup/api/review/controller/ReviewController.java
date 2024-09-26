package com.kosa.chanzipup.api.review.controller;

import com.kosa.chanzipup.api.review.controller.query.ReviewQueryService;
import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.ReviewDetail;
import com.kosa.chanzipup.api.review.controller.response.ReviewRegisterResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewResponse;
import com.kosa.chanzipup.api.review.controller.response.create.ReviewCreationPage;
import com.kosa.chanzipup.api.review.service.ReviewImagesService;
import com.kosa.chanzipup.api.review.service.ReviewService;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.application.PathMatchService;
import com.kosa.chanzipup.application.images.ImageService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.review.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    private final ImageService imageService;

    private final ReviewQueryService reviewQueryService;

    private final ReviewImagesService reviewImagesService;

    private final PathMatchService pathMatchService;

   @PostMapping
    public ResponseEntity<ReviewRegisterResponse> createReview(@AuthenticationPrincipal UnifiedUserDetails userDetails,
                                                               @RequestBody ReviewRegisterRequest request) {
        log.info("review startDate = {}", request.getWorkStartDate());

        return ResponseEntity.ok(reviewService.registerReview(request, userDetails.getName()));
    }

    @GetMapping
    public ResponseEntity<ReviewCreationPage> getRegisterPage(@RequestParam("requestId") Long requestId) {
        return ResponseEntity.ok(reviewQueryService.reviewCreationPage(requestId));
    }

    @PostMapping("/{reviewId}/images")
    public ResponseEntity<String> uploadReviewImages(@PathVariable("reviewId") Long reviewId, MultipartFile file) {
        String name = file.getName();
        String uploadEndPoint = imageService.store("reviews", file);
        String uploadFullPath = reviewImagesService.addReviewImage(reviewId, uploadEndPoint);
        log.info("name = {}, uploadFullPath = {}", name, uploadFullPath);
        return ResponseEntity.ok(uploadFullPath);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Boolean> updateReviewContent(@PathVariable("reviewId") Long reviewId,
                                                       @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        reviewService.updateReviewContent(reviewId, reviewUpdateRequest.getContent());
        // review 등록 성공 여부를 전달.
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<List<ReviewResponse>>> getAllReviewsWithPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviewsWithPage(pageable.getPageSize(), pageable.getPageNumber()));
    }

    // params
    @GetMapping("/mypage")
    public ResponseEntity<Page<List<ReviewResponse>>> getAllReviewsWithMyPage(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                                              @AuthenticationPrincipal UnifiedUserDetails userDetails) {
       return ResponseEntity.ok(Page.of(reviewService.getAllMemberWriteReviews(userDetails.getUsername()), pageable.getPageSize(), pageable.getPageNumber()));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetail> review(@PathVariable("reviewId") Long reviewId,
                                               @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(reviewQueryService.getUserDetail(reviewId, userDetails));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("reviewId") Long reviewId,
                                                @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        List<String> deleteImageUrls = reviewService.deleteReview(reviewId, userDetails.getUsername());
        imageService.deleteAllImages(deleteImageUrls);

        return ResponseEntity.ok(true);
    }
}
