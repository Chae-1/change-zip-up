package com.kosa.chanzipup.api.company.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CompanyRegisterRequest {

    @NotBlank(message = "회사 이름은 반드시 입력되어야 합니다.")
    private String companyName;

    @NotBlank(message = "이메일은 반드시 입력되어야 합니다.")
    private String email;

    private String password;

    private String phoneNumber;

    private String companyNumber;

    private String owner;

    private LocalDate publishDate;

}
