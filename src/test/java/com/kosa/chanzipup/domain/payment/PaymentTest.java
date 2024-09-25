package com.kosa.chanzipup.domain.payment;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

//    @DisplayName("날짜와 멤버십을 가지고 결제 정보를 생성한다. 단, pg사 결제를 하지 않았으므로 해당 결제 정보는 존재하지 않는다.")
//    @Test
//    void create() {
//        // given
//        LocalDateTime now = LocalDateTime.of(2024, 9, 10, 10, 00, 00);
//        MembershipType basic = new MembershipType(100, MembershipName.BASIC);
//        MembershipType platinum = new MembershipType(200, MembershipName.PREMIUM);
//        Company company = Company.ofNewCompany("email", "123", "123", "123", "123", "123", LocalDate.now(), "asdasd", "di");
//
//        // when
//        Payment payment1 = Payment.create(basic, company, now);
//        Payment payment2 = Payment.create(platinum, company, now);
//
//        // then
//        assertThat(payment1.getImpUid()).isNull();
//        assertThat(payment1.getRequestDate()).isEqualTo(now);
//    }
}