package com.kosa.chanzipup.domain.payment;

import static com.kosa.chanzipup.domain.payment.PaymentStatus.*;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membership.MembershipType;
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


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @ManyToOne
    @JoinColumn(name = "membership_type_id")
    private MembershipType membershipType;

    private Payment(MembershipType membershipType, PaymentStatus status, LocalDateTime paymentRequestDate, Company company) {
        this.status = status;
        this.membershipType = membershipType;
        this.merchantUid = createMerchantUid(paymentRequestDate);
        this.requestDate = paymentRequestDate;
        this.company = company;
    }

    private String createMerchantUid(LocalDateTime requestDateTime) {
        return String.format("%s.%s", requestDateTime.format(DateTimeFormatter.ISO_DATE),
                UUID.randomUUID().toString().substring(0, 20));
    }

    public static Payment create(MembershipType membershipType,
                                 Company company, LocalDateTime paymentRequestDateTime) {
        return new Payment(membershipType, PaymentStatus.CREATE, paymentRequestDateTime, company);
    }

    // 결제를 성공적으로 체결하면 상태를 변경시킨다.
    public void success(String impUid, int paidAmount, LocalDateTime completeDate) {
        // 이미 존재하는 결제라면
        if (alreadyPaidPayment(impUid)) {
            throw new PaymentException("결재에 실패하였습니다.");
        }

        if (isNotMatchPaymentPrice(paidAmount)) {
            cancel();
            throw new PaymentNotValidException("실제 결제 금액과 결제해야하는 금액이 다릅니다.", impUid, id);
        }

        this.impUid = impUid;
        this.completeDate = completeDate;
        this.status = COMPLETE;
    }

    private boolean alreadyPaidPayment(String impUid) {
        return impUid == null || impUid.isBlank();
    }

    public boolean isNotMatchPaymentPrice(int paymentPrice) {
        return membershipType.getPrice() != paymentPrice;
    }

    public void cancel() {
        this.status = CANCEL;
    }
}
