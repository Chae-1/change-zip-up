package com.kosa.chanzipup.api.company.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CompanyUpdateRequest {
    private List<Long> updateServices;
}