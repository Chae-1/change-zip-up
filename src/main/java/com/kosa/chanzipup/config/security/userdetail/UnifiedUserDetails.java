package com.kosa.chanzipup.config.security.userdetail;

import com.kosa.chanzipup.domain.account.member.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UnifiedUserDetails extends OAuth2User, UserDetails {
    String getPassword();

    Member toEntity(String encodedPassword);
}
