package com.kosa.chanzipup.api.estimate.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class EstimateRequest {

    @NotBlank(message = "인테리어 할 건물은 반드시 입력되어야 합니다.")
    private Long buildingTypeId;

    @NotNull(message = "평수(면적)는 반드시 입력되어야 합니다.")
    private int floor;

    @NotEmpty(message = "시공 분야는 반드시 입력되어야 합니다.")
    private List<Long> constructionTypeIds;

    @NotBlank(message = "공사 예정일은 반드시 입력되어야 합니다.")
    private String schedule;

    @NotBlank(message = "인테리어 예산은 반드시 입력되어야 합니다.")
    private String budget;

    @NotBlank(message = "주소는 반드시 입력되어야 합니다.")
    private String address;

    private String detailedAddress;

    private LocalDate measureDate;
}
