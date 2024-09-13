package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.estimate.EstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class EstimateResult {
    private Long companyId;

    private String companyName;

    private String username;

    private LocalDate estimateRequestDate;

    private List<String> constructionTypes;

    private String schedule;

    private String budget;

    private String constructionAddress;

    private int floor;

    private BuildingType buildingType;

    public static EstimateResponse of(Company company, EstimateRequest request) {
        return null;  // 여기서부터 수정 필요
    }
}
