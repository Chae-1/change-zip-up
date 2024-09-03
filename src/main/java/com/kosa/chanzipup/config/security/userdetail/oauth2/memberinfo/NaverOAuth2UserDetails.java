package com.kosa.chanzipup.config.security.userdetail.oauth2.memberinfo;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NaverOAuth2UserDetails implements UnifiedUserDetails {

    private final Map<String, Object> attributes;
    private final String registrationId; // kakao, naver 식별자.

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(AccountRole.USER.getRole()));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("email"));
    }


    @Override
    public String getPassword() {
        return String.format("%s_%s", registrationId, attributes.get("id"));
    }

    @Override
    public String getUsername() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public Member toEntity(String encodedPassword) {
        String name = String.valueOf(attributes.get("name"));
        String email = String.valueOf(attributes.get("email"));
        String nickName = String.valueOf(attributes.get("nickname"));
        String mobile = String.valueOf(attributes.get("mobile"));

        return Member.ofSocial(AccountRole.USER, email, encodedPassword, mobile,
                SocialType.NAVER, nickName, name);
    }
}
