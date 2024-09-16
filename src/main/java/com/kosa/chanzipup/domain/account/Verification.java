package com.kosa.chanzipup.domain.account;

import jakarta.persistence.*;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String verificationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private Verification(Account account, String verificationCode) {
        this.account = account;
        this.verificationCode = verificationCode;
    }

    public static Verification create(Account account) {
        String verificationCode = UUID.randomUUID().toString();
        return new Verification(account, verificationCode);
    }

}
