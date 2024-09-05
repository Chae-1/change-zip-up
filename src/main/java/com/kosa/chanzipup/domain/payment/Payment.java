package com.kosa.chanzipup.domain.payment;

import com.kosa.chanzipup.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUid; // PG사에서 발급해준 impUid

    // merchant_uid 20240504_BASIC_0000001
    // 오늘날짜 + 멤버십 등급 + 0000001
    // LocalDate.now() + BASIC +
    private String merchantUid; // 구매 id

    private int price;

    // 위변조 확인을 위한 결제 상태 확인
    private PaymentStatus status;
}
