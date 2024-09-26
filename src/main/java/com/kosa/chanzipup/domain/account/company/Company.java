package com.kosa.chanzipup.domain.account.company;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRole;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends Account {

    private String companyName;

    private String companyNumber;

    private String companyDesc;

    private LocalDate publishDate;

    private String address;

    private String owner;

    private double rating;

    private String companyLogoUrl;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
    private List<CompanyConstructionType> constructionTypes = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Estimate> estimates;

    @Builder
    private Company(AccountRole accountRole, String email, String password, String phoneNumber, boolean isVerified,
                    String companyNumber, String companyName, String owner, LocalDate publishDate, String address,
                    String companyDesc, String companyLogoUrl) {
        super(accountRole, email, password, phoneNumber, isVerified);
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.owner = owner;
        this.publishDate = publishDate;
        this.address = address;
        this.companyLogoUrl = companyLogoUrl;
        this.companyDesc = companyDesc;
    }

    public static Company ofNewCompany(String email, String companyName, String password,
                                       String phoneNumber, String owner, String companyNumber,
                                       LocalDate publishDate, String address, String companyDesc, String companyLogoUrl) {

        return Company.builder()
                .email(email)
                .password(password)
                .companyNumber(companyNumber)
                .accountRole(AccountRole.COMPANY)
                .phoneNumber(phoneNumber)
                .companyName(companyName)
                .isVerified(false)
                .companyLogoUrl(companyLogoUrl)
                .owner(owner)
                .publishDate(publishDate)
                .address(address)
                .companyDesc(companyDesc)
                .build();
    }

    public static Company ofNewCompanyForTest(String email, String companyName, String password,
                                       String phoneNumber, String owner, String companyNumber,
                                       LocalDate publishDate, String address, String companyDesc) {

        return Company.builder()
                .email(email)
                .password(password)
                .companyNumber(companyNumber)
                .accountRole(AccountRole.COMPANY)
                .phoneNumber(phoneNumber)
                .companyName(companyName)
                .isVerified(true)
                .owner(owner)
                .publishDate(publishDate)
                .address(address)
                .companyDesc(companyDesc)
                .build();
    }

    public void addConstructionType(CompanyConstructionType constructionType) {
        constructionTypes.add(constructionType);
    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public String getNickName() {
        return companyName;
    }

    @Override
    public String getName() {
        return owner;
    }

    // 현재 활성 중인 멤버십은 단 하나밖에 존재하지 않는다.
    public Membership getActiveMembership() {
        return memberships.stream()
                .filter(Membership::isValid)
                .findAny()
                .orElse(null);
    }

    public void updateRating(double companyRating) {
        this.rating = companyRating;
    }

    public void addConstructionTypes(List<ConstructionType> constructionTypes) {
        constructionTypes
                .forEach(type -> addConstructionType(new CompanyConstructionType(type, this)));
    }

    public void removeAllConstructionTypes() {
        constructionTypes.clear();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
