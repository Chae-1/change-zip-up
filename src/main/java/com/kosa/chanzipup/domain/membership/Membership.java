package com.kosa.chanzipup.domain.membership;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.account.company.Company;
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
    private MembershipType membershipType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String impUid;

    private Membership(LocalDateTime startDateTime, LocalDateTime endDateTime,
                       MembershipType membershipType, Company company, String impUid) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.membershipType = membershipType;
        this.company = company;
    }

    public static Membership ofNewMembership(Company company, MembershipType membershipType,
                                             LocalDateTime startDateTime,
                                             LocalDateTime endDateTime, String impUid) {
        return new Membership(startDateTime, endDateTime, membershipType, company, impUid);
    }

    public boolean isValid() {
        return endDateTime.isAfter(startDateTime);
    }
}
