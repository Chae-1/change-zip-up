package com.kosa.chanzipup.domain.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"refreshToken"})
    Optional<Account> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
