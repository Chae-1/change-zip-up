package com.kosa.chanzipup.domain.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberType;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("회원에 종류에 상관없이 이메일로 계정을 조회할 수 있다.")
    @Test
    void findByEmail() {
        // given
        Member member1 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com3", "1234",
                "010-1919-1919", MemberType.LOCAL, "hyengil", "형일1");
        Member member2 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com1", "1234",
                "010-1919-1919", MemberType.LOCAL, "hyengil", "형일2");
        Member member3 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com2", "1234",
                "010-1919-1919", MemberType.LOCAL, "hyengil", "형일3");
        accountRepository.saveAll(List.of(member1, member2, member3));
        // when
        Optional<Account> findAccount = accountRepository.findByEmail("hyeongil@naver.com1");

        // then
        assertThat(findAccount).contains(member2);
    }
}