package com.kosa.chanzipup.api.memberships.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MembershipHistories {
    private int price;
    private String typeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String companyName;
    private String email;

    public MembershipHistories(Membership membership) {
        MembershipType membershipType = membership.getMembershipType();
        Company company = membership.getCompany();
        this.price = membershipType.getPrice();
        this.typeName = String.valueOf(membershipType.getName());
        this.startDate = LocalDate.from(membership.getStartDateTime());
        this.endDate = LocalDate.from(membership.getEndDateTime());
        this.companyName = company.getCompanyName();
        this.email = company.getEmail();
    }
}
