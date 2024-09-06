package com.kosa.chanzipup.api.payment.controller;


import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.payment.controller.request.PaymentResult;
import com.kosa.chanzipup.api.payment.controller.response.PaymentPrepareResponse;
import com.kosa.chanzipup.api.payment.service.PaymentService;
import com.kosa.chanzipup.api.payment.service.query.PaymentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1. 결제를 위해서, 다음 결제 merchant_uid을 생성해서 클라이언트에 전달한다.
// -> 넘버링을 통해 제공하는건 동시성 이슈 고려사항이 많이 존재함.
// -> 만약, 필요하다면 나중에 이 부분을 수정하자.
// -> 지금은 주문 생성시 매번 UUID로 생성하자.
// 2. 결제 정보를 미리 생성한다.
// -> Payment 테이블에 데이터를 생성
// -> PG사에 결제를 요청하기 위해서는 서버에서 유지하는 상품번호가 존재해야한다.
// ->
// 3.

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payment/prepare")
    public ApiResponse<PaymentPrepareResponse> generatePaymentInfoForClient(@RequestBody Long membershipId) {
        // 1. 일단 회원 정보를 조회해서 이 회원이 기업 회원이나 ADMIN 권한이 존재하는지 확인한다.

        // 2. 확인 이후, 결제정보를 생성한다.
        return ApiResponse.ok(paymentService.createNewPayment("Auth@email.com", membershipId));
    }

    @PostMapping("/api/payment/complete")
    public ApiResponse<String> confirmPayment(@RequestBody @Validated PaymentResult paymentResult) {

        return ApiResponse.ok(null);
    }
}
