package com.kosa.chanzipup.domain.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    @Query("select v from Verification v join fetch v.account where v.verificationCode = :verificationCode")
    Optional<Verification> findByVerificationCode(@Param("verificationCode") String verificationCode);
}
