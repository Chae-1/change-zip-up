package com.kosa.chanzipup.domain.account.member;

import com.kosa.chanzipup.config.security.userdetail.DetailedUser;
import com.kosa.chanzipup.config.security.userdetail.oauth2.memberinfo.NaverOAuth2User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum SocialType {
    NAVER("naver"),
    KAKAO("kakao"),
    LOCAL("local");

    private final String registrationId;

    public static SocialType from(String registrationId) {
        return null;
    }

    public static DetailedUser convertToSocialUser(Map<String, Object> attributes, String registrationId) {
        if (NAVER.getRegistrationId().equals(registrationId)) {
            // 현재 전달된 회원 정보가 naver 정보이면,
            return new NaverOAuth2User((Map<String, Object>) attributes.get("response"), registrationId);
        } else if (KAKAO.getRegistrationId().equals(registrationId)) {
            // 현재 전달된 회원 정보가 kakao 정보이면,
        }
        return null;
    }
}
