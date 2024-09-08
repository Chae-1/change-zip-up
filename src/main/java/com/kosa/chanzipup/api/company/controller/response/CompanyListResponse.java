package com.kosa.chanzipup.api.company.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyListResponse {
    private Long id;

    private String companyName;

    private String companyDesc;

    private String companyLogoUrl;

    private float rating;

    private List<String> services;
}
