package com.kosa.chanzipup.domain.account.member;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("member")
@NoArgsConstructor
public class Member extends Account {

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    private String nickName;

    private String name;

    @Builder
    private Member(AccountRole accountRole, String email, String password, boolean isVerified,
                   String phoneNumber, SocialType socialType, String nickName, String name) {
        super(accountRole, email, password, phoneNumber, isVerified);
        this.socialType = socialType;
        this.nickName = nickName;
        this.name = name;
    }

    // 소셜 로그인으로 가입하는 경우, 별도의 이메일 인증이 필요하지 않다.
    public static Member ofSocial(AccountRole accountRole, String email, String password,
                                  String phoneNumber, SocialType socialType,
                                  String nickName, String name) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .accountRole(accountRole)
                .socialType(socialType)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .isVerified(true)
                .build();
    }

    public static Member ofLocal(AccountRole accountRole, String email, String password,
                                 String phoneNumber, SocialType socialType, String nickName, String name) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .accountRole(accountRole)
                .socialType(socialType)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .isVerified(false)
                .build();
    }
}
