package com.kosa.chanzipup.api.review.controller;

import com.kosa.chanzipup.api.review.controller.query.ReviewQueryService;
import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.ReviewDetail;
import com.kosa.chanzipup.api.review.controller.response.ReviewRegisterResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewResponse;
import com.kosa.chanzipup.api.review.service.ReviewImagesService;
import com.kosa.chanzipup.api.review.service.ReviewService;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.application.PathMatchService;
import com.kosa.chanzipup.application.images.ImageService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.review.ReviewContent;
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

    // 후기 등록
    // 1. 기본 내용을 삽입한다.
    // 2. 이미지들을 등록하고
    // 3. 등록된 이미지를 포함한 컨텐츠를 업데이트.
    // 왜 이렇게 했나?
    // 텍스트 에디터가 이미지를 저장할 때 Base64 인코딩을 기본적으로 사용하는 이유로 저장되는 content의 크기가 매우 크다.
    // 그래서 이 부분을 줄이고자 서버를 경유해서 이미지를 특정 경로(s3, local)에 저장한 후 이미지에 접근할 수 있는 url을 반환하여
    // base64 인코딩 내용을 대체하여 저장했다.
    // 이렇게 대체된 content의 크기는 비교할 수 없을 정도로 크기가 작아지고, 마지막에 이 content를 update하면 된다.
    @PostMapping
    public ResponseEntity<ReviewRegisterResponse> createReview(@AuthenticationPrincipal UnifiedUserDetails userDetails,
                                                               @RequestBody ReviewRegisterRequest request) {
        log.info("review startDate = {}", request.getWorkStartDate());

        return ResponseEntity.ok(reviewService.registerReview(request, userDetails.getName()));
    }

    @GetMapping("/create")
    public ResponseEntity<?> getRegisterPage() {
        return ResponseEntity.ok(reviewQueryService.reviewCreationPage());
    }

    @PostMapping("/{reviewId}/images")
    public ResponseEntity<String> uploadReviewImages(@PathVariable("reviewId") Long reviewId, MultipartFile file) {
        String name = file.getName();
        String uploadEndPoint = imageService.store("reviews", file);
        String uploadFullPath = reviewImagesService.addReviewImage(reviewId, pathMatchService.match(uploadEndPoint));
        log.info("name = {}, uploadFullPath = {}", name, uploadFullPath);
        return ResponseEntity.ok(uploadFullPath);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Boolean> updateReviewContent(@PathVariable("reviewId") Long reviewId,
                                                       @RequestBody ReviewContent reviewContent) {
        reviewService.updateReviewContent(reviewId, reviewContent.getContent());
        // review 등록 성공 여부를 전달.
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllCompany(@PageableDefault Pageable pageable) {
        List<ReviewResponse> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<List<ReviewResponse>>> getAllCompaniesWithPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviewsWithPage(pageable.getPageNumber(), pageable.getPageSize()));
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
