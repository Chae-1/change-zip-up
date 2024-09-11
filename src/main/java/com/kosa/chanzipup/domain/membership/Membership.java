package com.kosa.chanzipup.domain.membership;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_internal_id")
    private MembershipInternal membershipInternal;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Membership(LocalDateTime startDateTime, LocalDateTime endDateTime, MembershipInternal membershipInternal, Company company) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.membershipInternal = membershipInternal;
        this.company = company;
    }

    public static Membership ofNewMembership(Company company, MembershipInternal membershipInternal,
                                             LocalDateTime startDateTime,
                                             LocalDateTime endDateTime) {
        return new Membership(startDateTime, endDateTime, membershipInternal, company);
    }

}
