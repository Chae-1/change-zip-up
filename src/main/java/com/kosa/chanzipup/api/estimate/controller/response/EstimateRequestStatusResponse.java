package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import com.kosa.chanzipup.domain.estimate.EstimateRequestStatus;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EstimateRequestStatusResponse {

    private Long requestId;

    private String address;

    private String fullAddress;

    private Integer floor;

    private String budget;

    private String schedule;

    private String nickName;

    private String buildingTypeName;

    private LocalDateTime regDate;

    private List<String> constructionTypes;

    private boolean isSend;

    private EstimateRequestStatus estimateRequestStatus;

    private EstimateStatus estimateStatus;

    private Long estimateId;

    public EstimateRequestStatusResponse(EstimateRequest estimateRequest, boolean isSend, EstimateRequestStatus estimateRequestStatus) {
        this.requestId = estimateRequest.getId();
        this.address = estimateRequest.getAddress();
        this.fullAddress = estimateRequest.getFullAddress();
        this.floor = estimateRequest.getFloor();
        this.budget = estimateRequest.getBudget();
        this.schedule = estimateRequest.getSchedule();
        this.nickName = estimateRequest.getMember().getNickName();
        this.buildingTypeName = estimateRequest.getBuildingType().getName();
        this.regDate = estimateRequest.getRegDate();
        this.constructionTypes = estimateRequest.getConstructionTypes()
                .stream()
                .map(type -> type.getTypeName())
                .toList();
        this.isSend = isSend;
        this.estimateRequestStatus = estimateRequestStatus;
    }

    public EstimateRequestStatusResponse(Estimate estimate) {
        this(estimate.getEstimateRequest(), false, null);
        estimateStatus = null;
    }

    public EstimateRequestStatusResponse(EstimateRequest estimateRequest, List<Estimate> requestEstimates) {
        this(estimateRequest, requestEstimates != null && !requestEstimates.isEmpty(), null);
        if (isSend) {
            Estimate estimate = requestEstimates.get(0);
            estimateStatus = estimate.getEstimateStatus();
            estimateId = estimate.getId();
        } else {
            estimateStatus = null;
        }
    }
}
