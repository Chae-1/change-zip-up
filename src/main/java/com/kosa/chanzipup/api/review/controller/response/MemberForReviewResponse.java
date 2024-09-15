package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.account.member.Member;
import lombok.Getter;

@Getter
public class MemberForReviewResponse {

    private String nickName;

    public MemberForReviewResponse(Member member) {
        this.nickName = member.getNickName();
    }
}
