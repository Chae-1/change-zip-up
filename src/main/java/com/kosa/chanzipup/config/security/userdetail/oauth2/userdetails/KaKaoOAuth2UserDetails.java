package com.kosa.chanzipup.config.security.userdetail.oauth2.userdetails;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KaKaoOAuth2UserDetails implements UnifiedUserDetails {

    private final Map<String, Object> attributes;
    private final String registrationId; // kakao, naver 식별자.
    private final Map<String, String> properties;
    private final Map<String, Object> kakaoAccountMap;
    public KaKaoOAuth2UserDetails(Map<String, Object> attributes, String registrationId) {
        this.attributes = attributes;
        this.properties = (Map<String, String>) attributes.get("properties");
        this.kakaoAccountMap = (Map<String, Object>) attributes.get("kakao_account");
        this.registrationId = registrationId;
    }

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
        return String.valueOf(kakaoAccountMap.get("email"));
    }

    @Override
    public String nickName() {
        return String.valueOf(properties.get("nickname"));
    }

    @Override
    public String getPassword() {
        return String.format("%s_%s", registrationId, attributes.get("id"));
    }

    @Override
    public String getRegisteredId() {
        return registrationId;
    }

    @Override
    public String getUsername() {
        return String.valueOf(kakaoAccountMap.get("email"));
    }

    @Override
    public Member toEntity(String encodedPassword) {

        String nickName = String.valueOf(properties.get("nickname"));
        String email = String.valueOf(kakaoAccountMap.get("email"));

        return Member.ofSocial(AccountRole.USER, email, encodedPassword, "01000000000",
                MemberType.KAKAO, nickName, "실명없음");
    }
}
