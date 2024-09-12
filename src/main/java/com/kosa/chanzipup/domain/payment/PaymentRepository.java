package com.kosa.chanzipup.domain.payment;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @EntityGraph(attributePaths = {"membershipType", "company"})
    Optional<Payment> findByMerchantUid(@Param("merchantUid") String merchantUid);
}
