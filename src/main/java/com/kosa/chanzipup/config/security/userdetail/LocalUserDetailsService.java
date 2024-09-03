package com.kosa.chanzipup.config.security.userdetail;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정 정보가 존재하지 않습니다."));
        return new LocalUserDetailsDetails(account);
    }
}
