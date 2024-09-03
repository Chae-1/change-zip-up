package com.kosa.chanzipup.domain.account.token;

import com.kosa.chanzipup.domain.account.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expireDateTime;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private RefreshToken(String token, LocalDateTime expireDateTime, Account account) {
        this.expireDateTime = expireDateTime;
        this.token = token;
        this.account = account;
    }

    public static RefreshToken of(String token, LocalDateTime expireDateTime, Account account) {
        return new RefreshToken(token, expireDateTime, account);
    }
}
