package com.kosa.chanzipup.domain.estimate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstimateStatus {
    COUNSELING("상담중"),
    ONGOING("진행중"),
    COMPLETION("완료"),
    CANCELLATION("취소");

    private final String status;
}
