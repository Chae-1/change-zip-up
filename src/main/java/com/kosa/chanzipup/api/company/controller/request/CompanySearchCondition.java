package com.kosa.chanzipup.api.company.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanySearchCondition {
    private String city;
    private String district;
    private List<Long> services;
}
