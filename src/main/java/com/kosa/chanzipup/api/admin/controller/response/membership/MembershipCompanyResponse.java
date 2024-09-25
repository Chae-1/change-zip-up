package com.kosa.chanzipup.api.admin.controller.response.membership;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MembershipCompanyResponse {

    private LocalDate endDate;
    private LocalDate startDate;
    private MembershipName membershipName;
    private Long membershipId;
    private int membershipPrice;
    private boolean isValidMembership;

    private Long companyId;
    private String companyName;

    public MembershipCompanyResponse(Membership membership) {
        Company company = membership.getCompany();
        this.companyId = company.getId();

        LocalDateTime startDateTime = membership.getStartDateTime();
        LocalDateTime endDateTime = membership.getEndDateTime();
        this.startDate = startDateTime.toLocalDate();
        this.endDate = endDateTime.toLocalDate();
        this.isValidMembership = endDate.isAfter(startDate);

        this.membershipId = membership.getId();
        this.membershipPrice = membership.getMembershipType().getPrice();
        this.membershipName = membership.getMembershipType().getName();

        this.companyName = company.getCompanyName();

    }
}
