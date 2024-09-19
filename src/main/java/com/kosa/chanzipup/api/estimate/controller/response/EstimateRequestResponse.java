package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.estimate.EstimateConstructionType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EstimateRequestResponse {

    private Long requestId;

    private String fullAddress;

    private Integer floor;

    private String budget;

    private String schedule;

    private String nickName;

    private String buildingTypeName;

    private LocalDateTime regDate;

    private List<String> constructionTypes;


    public EstimateRequestResponse(Long requestId, String fullAddress, Integer floor, String budget, String schedule, String nickName
            , String buildingTypeName, LocalDateTime regDate, List<String> constructionTypes) {
        this.requestId = requestId;
        this.fullAddress = fullAddress;
        this.floor = floor;
        this.budget = budget;
        this.schedule = schedule;
        this.nickName = nickName;
        this.buildingTypeName = buildingTypeName;
        this.regDate = regDate;
        this.constructionTypes = constructionTypes;
    }
}