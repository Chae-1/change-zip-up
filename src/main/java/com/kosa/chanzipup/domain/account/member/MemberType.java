package com.kosa.chanzipup.domain.account.member;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.config.security.userdetail.oauth2.memberinfo.NaverOAuth2UserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum MemberType {
    NAVER("naver"),
    KAKAO("kakao"),
    LOCAL("local");

    private final String registrationId;

    public static MemberType from(String registrationId) {

        return Arrays.stream(values())
                .filter(type -> type.registrationId.equalsIgnoreCase(registrationId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 로그인 방식입니다."));
    }

    public static boolean isSocial(String registeredId) {
        MemberType type = MemberType.from(registeredId);
        List<MemberType> socialTypes = List.of(KAKAO, NAVER);
        return socialTypes.contains(type);
    }

    public static UnifiedUserDetails convertToSocialUser(Map<String, Object> attributes, String registrationId) {
        MemberType memberType = from(registrationId);

        if (NAVER == memberType) {
            // 현재 전달된 회원 정보가 naver 정보이면,
            return new NaverOAuth2UserDetails((Map<String, Object>) attributes.get("response"), registrationId);
        } else if (KAKAO == memberType) {
            // 현재 전달된 회원 정보가 kakao 정보이면,
        }
        throw new IllegalArgumentException("지원하지 않는 로그인 방식입니다.");
    }

}
