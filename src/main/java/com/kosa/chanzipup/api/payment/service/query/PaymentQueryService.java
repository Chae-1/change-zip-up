package com.kosa.chanzipup.api.payment.service.query;

import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {
    private final PaymentRepository paymentRepository;
    private final MembershipTypeRepository membershipTypeRepository;


}
