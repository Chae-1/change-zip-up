package com.kosa.chanzipup.domain.account.member;

import com.kosa.chanzipup.domain.account.AccountRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일로 회원 정보를 찾을 수 있다.")
    @Test
    void findByEmail() {
        // given
        Member member1 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com3", "1234",
                "010-1919-1919", SocialType.LOCAL, "hyengil", "형일1");
        Member member2 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com1", "1234",
                "010-1919-1919", SocialType.LOCAL, "hyengil", "형일2");
        Member member3 = Member.ofLocal(AccountRole.USER, "hyeongil@naver.com2", "1234",
                "010-1919-1919", SocialType.LOCAL, "hyengil", "형일3");
        memberRepository.saveAll(List.of(member1, member2, member3));

        // when
        Optional<Member> findMember = memberRepository.findByEmail("hyeongil@naver.com1");

        // then
        assertThat(findMember).contains(member2);
    }

}