package com.kosa.chanzipup.domain.estimate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstimateStatus {
    SENT("고객에게 견적을 보냈다."),      // 보낸 견적
    RECEIVED("고객에게 견적을 받았다."),  // 받은 견적
    ACCEPTED("견적이 성공적으로 채택되어 진행 중인 상태."),  // 진행중인 견적
    COMPLETE("요청에 대해 완료한 상태."), // 완료된 견적
    REJECTED("견적을 거절 당하거나 다른 회사에 의해 견적 요청이 완료되었다.");  // 받은 견적

    private final String status;
}
