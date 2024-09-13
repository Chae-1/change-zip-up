package com.kosa.chanzipup.api.estimate.controller.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class EstimateResponseDTO {
    private Long id;
    private Long buildingTypeId;
    private int floor;
    private List<Long> constructionTypeIds;
    private String schedule;
    private String budget;
    private String address;
    private String detailedAddress;
    private LocalDate measureDate;
}
