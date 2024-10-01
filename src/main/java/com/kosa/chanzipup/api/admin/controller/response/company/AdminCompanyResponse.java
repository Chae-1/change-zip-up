package com.kosa.chanzipup.api.admin.controller.response.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class AdminCompanyResponse {
    private Long id;
    private String companyName;
    private String companyNumber;
    private String email;
    private LocalDate publishedDate;
    private String phoneNumber;
    private boolean isVerified;
    private String companyLogoUrl;
    private String address;
    private String owner;
    private double rating;
    private String membership;
    private LocalDate createAt;
}
