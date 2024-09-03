package com.kosa.chanzipup.domain.account;

import com.kosa.chanzipup.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type")
public abstract class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    private String email;

    private String password;

    private String phoneNumber;

    private boolean isVerified;

    protected Account(AccountRole accountRole, String email, String password, String phoneNumber) {
        this(accountRole, email, password, phoneNumber, false);
    }

    protected Account(AccountRole accountRole, String email, String password, String phoneNumber, boolean isVerified) {
        this.accountRole = accountRole;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
    }
}
