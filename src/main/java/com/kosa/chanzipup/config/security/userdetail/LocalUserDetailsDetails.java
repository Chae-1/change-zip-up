package com.kosa.chanzipup.config.security.userdetail;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.member.Member;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LocalUserDetailsDetails implements UnifiedUserDetails {

    private final String email;
    private final AccountRole accountRole;
    private final String password;


    public LocalUserDetailsDetails(Account account) {
        this.email = account.getEmail();
        this.accountRole = account.getAccountRole();
        this.password = getPassword();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(accountRole.getRole()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Member toEntity(String encodedPassword) {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getName() {
        return email;
    }
}
