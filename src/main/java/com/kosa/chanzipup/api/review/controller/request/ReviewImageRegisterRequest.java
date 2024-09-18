package com.kosa.chanzipup.api.review.controller.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ReviewImageRegisterRequest {
    private Long reviewId;
    private List<MultipartFile> files;
}
