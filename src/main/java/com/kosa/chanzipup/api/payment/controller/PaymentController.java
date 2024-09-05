package com.kosa.chanzipup.api.payment.controller;


import com.kosa.chanzipup.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    // 1. 결제를 위해서, 다음 결제 merchant_uid을 생성해서 클라이언트에 전달한다.
    // -> 동시성 이슈로 절대로 X
    // 2. 결제 정보를 미리 생성한다.
    //
    //- 주문 고유 번호(merchant_uid) 관련 유의사항.
    //  - 주문 고유 번호는 개별 결제 요청을 구분하기 위해 사용되는 문자열입니다.
    //  - 따라서 주문 고유 번호는 결제 요청 시 항상 고유한 값으로 채번되어야 하며, 결제 완료 이후 결제 기록 조회나 위변조 대사 작업 시 사용되기 때문에 고객사 DB 상에 별도로 저장해야 합니다.
    //

    @PostMapping("/api/payment/complete")
    public ApiResponse<String> confirmPayment(@RequestBody) {

    }


}
