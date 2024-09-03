package com.kosa.chanzipup.config.security.userdetail.oauth2;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.account.member.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // oauth2 server 에서 조회한 정보를 조회한다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        // oauth2 서버 도메인에 따른 회원 정보를 생성한다.
        // OAuth2User Interface가 제공하는 것이 너무 부족하다.
        // DetailedUser 인터페이스를 통해 확장하자.
        String registrationId = clientRegistration.getRegistrationId();
        UnifiedUserDetails unifiedUserDetails = SocialType.convertToSocialUser(oAuth2User.getAttributes(), registrationId);

        // 1. 기존 회원일 경우, 그대로 반환한다.
        Optional<Member> findMember = memberRepository.findByEmail(unifiedUserDetails.getUsername());
        if (findMember.isPresent()) {
            return unifiedUserDetails;
        }

        // 2. 새로운 회원일 경우, 가입을 수행한 뒤 OAuth2User 를 반환한다.
        String encodedPassword = passwordEncoder.encode(unifiedUserDetails.getPassword());
        Member member = unifiedUserDetails.toEntity(encodedPassword);
        memberRepository.save(member);
        return unifiedUserDetails;
    }
}
