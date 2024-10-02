package com.kosa.chanzipup.domain.account.member;

import com.kosa.chanzipup.domain.account.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findByAccountRole(AccountRole role);
}
