package com.kosa.chanzipup.api.payment.service;

import com.kosa.chanzipup.domain.membershipinternal.MembershipInternalRepository;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    MembershipInternalRepository membershipInternalRepository;

    @DisplayName("")
    @Test
    void createNewPayment() {
        // given

        // when

        // then
    }
}