package com.kosa.chanzipup.config.security.userdetail;

import com.kosa.chanzipup.domain.account.member.Member;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface DetailedUser extends OAuth2User {

    String getEmail();

    String getPassword();

    Member toEntity(String encodedPassword);
}
