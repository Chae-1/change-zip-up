package com.kosa.chanzipup.api.payment.service;

import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    MembershipTypeRepository membershipTypeRepository;

    @DisplayName("")
    @Test
    void createNewPayment() {
        // given

        // when

        // then
    }
}