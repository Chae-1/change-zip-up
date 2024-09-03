package com.kosa.chanzipup.domain.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(@Param("email") String email);
}
