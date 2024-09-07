package com.kosa.chanzipup.api.company.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CompanyRegisterRequest {

    @NotBlank(message = "회사 이름은 반드시 입력되어야 합니다.")
    private String companyName;

    @NotBlank(message = "이메일은 반드시 입력되어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 반드시 입력되어야 합니다.")
    private String password;

    @NotBlank(message = "전화번호는 반드시 입력되어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "사업자번호는 반드시 입력되어야 합니다.")
    private String companyNumber;

    @NotBlank(message = "대표자명은 반드시 입력되어야 합니다.")
    private String owner;

    @NotBlank(message = "개업날짜는 반드시 입력되어야 합니다.")
    private LocalDate publishDate;

    private String address;

    private String companyDesc;

    @NotBlank(message = "시공 서비스는 반드시 선택되어야 합니다.")
    protected List<Long> constructionService = new ArrayList<>();

}
