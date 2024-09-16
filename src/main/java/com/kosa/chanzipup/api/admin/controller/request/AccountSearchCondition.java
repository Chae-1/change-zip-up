package com.kosa.chanzipup.api.admin.controller.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AccountSearchCondition {
    private String name;
    private String email;
    private boolean verified;
    private String accountType;
    private LocalDate signupDate;
}
