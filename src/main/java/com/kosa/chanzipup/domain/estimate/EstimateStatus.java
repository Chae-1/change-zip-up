package com.kosa.chanzipup.domain.estimate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstimateStatus {
    RECEIVED("고객에게 견적을 받았다."),  // 받은 견적
    SENT("고객에게 견적을 보냈다."),      // 보낸 견적
    ACCEPTED("견적이 성공적으로 채택되어 완료되거나 진행 중인 상태."),  // 완료 견적
    REJECTED("견적을 거절 당하거나 다른 회사에 의해 견적 요청이 완료되었다.");  // 받은 견적


//
//    WAITING("대기중"),
////    COUNSELING("상담중"),
//    ONGOING("진행중"),
//    COMPLETION("완료"),
//    CANCELLATION("취소");

    private final String status;
}
