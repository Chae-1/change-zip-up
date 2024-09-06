package com.kosa.chanzipup.domain.payment;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUid; // PG사에서 발급해준 impUid

    // todo: redis를 사용해서 유일한 값을 생성하기
    private String merchantUid; // 구매 id

    // 위변조 확인을 위한 결제 상태 확인
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private MembershipInternal membershipInternal;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    private Payment(MembershipInternal membershipInternal, PaymentStatus status, LocalDateTime paymentRequestDate) {
        this.status = status;
        this.membershipInternal = membershipInternal;
        this.merchantUid = createMerchantUid(paymentRequestDate);
        this.requestDate = paymentRequestDate;
    }

    private String createMerchantUid(LocalDateTime requestDateTime) {
        return String.format("%s.%s", requestDateTime.format(DateTimeFormatter.ISO_DATE),
                UUID.randomUUID());
    }

    public static Payment create(MembershipInternal membershipInternal, LocalDateTime paymentRequestDateTime) {
        return new Payment(membershipInternal, PaymentStatus.CREATE, paymentRequestDateTime);
    }

    // 결제를 성공적으로 체결하면 상태를 변경시킨다.
    public void success() {

    }

    public void fail() {

    }
}
