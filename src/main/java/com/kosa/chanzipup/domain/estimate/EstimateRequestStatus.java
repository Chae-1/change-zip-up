package com.kosa.chanzipup.domain.estimate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EstimateRequestStatus {
    WAITING("대기중"),
    ONGOING("진행중"),
    COMPLETE("완료"),
    WRITTENREVIEW("후기 작성 완료"),
    CANCELLATION("취소");

    private final String text;
}
