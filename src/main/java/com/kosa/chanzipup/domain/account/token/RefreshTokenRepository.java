package com.kosa.chanzipup.domain.account.token;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @EntityGraph(attributePaths = {"account"})
    @Query("select r from RefreshToken r")
    Optional<RefreshToken> findByToken(String token);
}
