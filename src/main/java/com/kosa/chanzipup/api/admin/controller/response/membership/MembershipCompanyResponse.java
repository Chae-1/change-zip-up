package com.kosa.chanzipup.api.admin.controller.response.membership;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipName;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MembershipCompanyResponse {

    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private MembershipName membershipName;
    private int membershipPrice;
    private boolean isValidMembership;

    private Long companyId;
    private String companyName;

    public MembershipCompanyResponse(Membership membership) {
        Company company = membership.getCompany();
        this.companyId = company.getId();
        this.startDateTime = membership.getStartDateTime();
        this.endDateTime = membership.getEndDateTime();
        this.membershipPrice = membership.getMembershipType().getPrice();
        this.membershipName = membership.getMembershipType().getName();
        this.isValidMembership = endDateTime.isAfter(startDateTime);
        this.companyName = company.getCompanyName();
    }
}
