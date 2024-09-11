package com.kosa.chanzipup.api.memberships.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MembershipResponse {
    private String membershipName;
    private int paymentPrice;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    public MembershipResponse(String membershipName, int paymentPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.membershipName = membershipName;
        this.paymentPrice = paymentPrice;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static MembershipResponse of(String membershipName, int paymentPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return MembershipResponse.builder()
                .membershipName(membershipName)
                .paymentPrice(paymentPrice)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
