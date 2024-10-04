package com.kosa.chanzipup.domain.membership;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Membership extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_type_id")
    private MembershipType membershipType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private Membership(LocalDateTime startDateTime, LocalDateTime endDateTime,
                       MembershipType membershipType, Company company, Payment payment) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.membershipType = membershipType;
        this.company = company;
        this.payment = payment;
    }

    public static Membership ofNewMembership(Company company, MembershipType membershipType,
                                             LocalDateTime startDateTime,
                                             LocalDateTime endDateTime, Payment payment) {
        return new Membership(startDateTime, endDateTime, membershipType, company, payment);
    }

    public boolean isValid() {
        return endDateTime.isAfter(startDateTime);
    }

    public MembershipName getMembershipName() {
        return membershipType.getName();
    }

    public void refundPayment(LocalDateTime refundDateTime) {
        payment.cancel();
        endDateTime = refundDateTime;
    }

    public void updatePrice(int price) {
        membershipType.updatePrice(price);
    }
}
