package com.kosa.chanzipup.domain.account.token;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @EntityGraph("account")
    Optional<RefreshToken> findByToken(String token);
}
