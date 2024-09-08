package com.kosa.chanzipup.domain.account.company;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRole;

import com.kosa.chanzipup.domain.companyConstructionType.CompanyConstructionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends Account {

    private String companyName;
    private String companyNumber;
    private String companyDesc;
    private String companyLogoUrl;
    private LocalDate publishDate;
    private String address;
    private String owner;
    private float rating;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
    private List<CompanyConstructionType> constructionTypes = new ArrayList<>();

    @Builder
    private Company(AccountRole accountRole, String email, String password, String phoneNumber, boolean isVerified,
                    String companyNumber, String companyName, String owner, LocalDate publishDate, String address,
                    String companyDesc) {
        super(accountRole, email, password, phoneNumber, isVerified);
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.owner = owner;
        this.publishDate = publishDate;
        this.address = address;
        this.companyDesc = companyDesc;
    }

    public static Company ofNewCompany(String email, String companyName, String password,
                                       String phoneNumber, String owner, String companyNumber,
                                       LocalDate publishDate, String address, String companyDesc) {
        return Company.builder()
                .email(email)
                .password(password)
                .companyNumber(companyNumber)
                .accountRole(AccountRole.COMPANY)
                .phoneNumber(phoneNumber)
                .companyName(companyName)
                .isVerified(false)
                .owner(owner)
                .publishDate(publishDate)
                .address(address)
                .companyDesc(companyDesc)
                .build();
    }

    public void addConstructionType(CompanyConstructionType constructionType) {
        constructionTypes.add(constructionType);
    }
}
