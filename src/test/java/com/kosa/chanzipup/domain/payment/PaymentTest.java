package com.kosa.chanzipup.domain.payment;

import com.kosa.chanzipup.domain.membershipinternal.MemberShipType;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @DisplayName("날짜와 멤버십을 가지고 결제 정보를 생성한다. 단, pg사 결제를 하지 않았으므로 해당 결제 정보는 존재하지 않는다.")
    @Test
    void create() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 9, 10, 10, 00, 00);
        MembershipInternal basic = new MembershipInternal(100, MemberShipType.BASIC);
        MembershipInternal platinum = new MembershipInternal(200, MemberShipType.PLATINUM);

        // when
        Payment payment1 = Payment.create(basic, now);
        Payment payment2 = Payment.create(platinum, now);

        // then
        assertThat(payment1.getImpUid()).isNull();
        assertThat(payment1.getRequestDate()).isEqualTo(now);
        assertThat(payment1.getMerchantUid())
                .startsWith(now.format(DateTimeFormatter.ISO_DATE))
                .matches("^\\d{4}-\\d{2}-\\d{2}.[0-9a-fA-F\\-]{36}$");
    }
}