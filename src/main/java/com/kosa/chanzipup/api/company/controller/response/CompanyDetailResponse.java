package com.kosa.chanzipup.api.company.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyDetailResponse {
    private Long id;

    private String companyName;

    private String companyNumber;

    private String owner;

    private String address;

    private String companyLogoUrl;

    private String phoneNumber;

    private String companyDesc;

    private LocalDate publishDate;

    private float rating;

    private List<String> services;

}