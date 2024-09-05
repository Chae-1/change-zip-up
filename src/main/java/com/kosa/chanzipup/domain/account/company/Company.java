package com.kosa.chanzipup.domain.account.company;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends Account {

    private String companyName;

    private String companyNumber; // 사업자등록번호

    private String companyDesc; // 길이에 따라 Lob일 수도 있음

    private String companyLogoUrl;

    /*
     * todo: 시공 지역 추가 예정
     */
    // private String area;

    private LocalDate publishDate;

    /*
     * todo: 외부 api 사용 예정
     */
    // private String address;

    /**
     * todo: 시공유형 추가 예정
     */


    // private String speciality;

    private String owner;

    private float rating;


    @Builder
    private Company(AccountRole accountRole, String email, String password, String phoneNumber, boolean isVerified,
                   String companyNumber, String companyName, String owner, LocalDate publishDate) {
        super(accountRole, email, password, phoneNumber, isVerified);
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.owner = owner;
        this.publishDate = publishDate;
    }

    public static Company ofNewCompany(String email, String companyName, String password,
                                       String phoneNumber, String owner, String companyNumber, LocalDate publishDate) {
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
                .build();
    }

}
