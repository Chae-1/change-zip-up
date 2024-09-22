package com.kosa.chanzipup.domain.account.token;

import com.kosa.chanzipup.domain.account.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expireDateTime;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "refreshToken")
    private Account account;

    private RefreshToken(String token, LocalDateTime expireDateTime) {
        this.expireDateTime = expireDateTime;
        this.token = token;
    }

    public static RefreshToken of(String token, LocalDateTime expireDateTime) {
        return new RefreshToken(token, expireDateTime);
    }

    public boolean isExpired(LocalDateTime today) {
        return expireDateTime.isBefore(today);
    }

    public void updateToken(String token, LocalDateTime expireDateTime) {
        this.token = token;
        this.expireDateTime = expireDateTime;
    }
}
