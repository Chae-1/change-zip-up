package com.kosa.chanzipup.api.estimate.controller.response;

import static java.util.stream.Collectors.toMap;

import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import java.util.Map;
import lombok.Getter;

@Getter
public class EstimateResponse {

    private Long estimateId;

    private EstimateStatus estimateStatus;

    private Map<String, Integer> constructionPrices;

    private int totalPrice;

    private SimpleMemberResponse memberResponse;

    private SimpleEstimateRequestResponse estimateResponse;




    public EstimateResponse(Estimate estimate) {
        EstimateRequest estimateRequest = estimate.getEstimateRequest();
        Member member = estimateRequest.getMember();

        this.estimateId = estimate.getId();
        this.estimateStatus = estimate.getEstimateStatus();


        this.memberResponse = new SimpleMemberResponse(member);
        this.estimateResponse = new SimpleEstimateRequestResponse(estimateRequest);

        this.constructionPrices = estimate
                .getEstimatePrices()
                .stream()
                .collect(toMap(price -> price.getConstructionType().getTypeName(), price -> price.getPrice()));

        this.totalPrice = constructionPrices
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

    }



    @Getter
    static class SimpleEstimateRequestResponse {
        private Long estimateRequestId;
        private String address;
        private String detailAddress;
        private String fullAddress;
        private String schedule;
        private String budget;
        private int floor;
        private String buildingType;

        public SimpleEstimateRequestResponse(EstimateRequest request) {
            this.estimateRequestId = request.getId();
            this.address = request.getAddress();
            this.detailAddress = request.getDetailedAddress();
            this.fullAddress = request.getFullAddress();
            this.schedule = request.getSchedule();
            this.budget = request.getBudget();
            this.floor = request.getFloor();
            this.buildingType = request.getBuildingType().getName();
        }
    }


    @Getter
    static class SimpleMemberResponse {
        private String nickName;
        private String phoneNumber;
        private String email;
        public SimpleMemberResponse(Member member) {
            this.nickName = member.getNickName();
            this.phoneNumber = member.getPhoneNumber();
            this.email = member.getEmail();
        }
    }

}
