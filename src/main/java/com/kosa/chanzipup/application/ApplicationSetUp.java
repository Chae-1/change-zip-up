package com.kosa.chanzipup.application;

import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.account.member.MemberType;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.membership.MembershipName;
import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationSetUp {
    private final MembershipTypeRepository membershipTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final BuildingTypeRepository buildingTypeRepository;


    //@PostConstruct
    public void init() {
        Member member1 =  Member.ofLocalForTest(AccountRole.USER, "test1@test.com", encoder.encode("qweqwe123!"), "010-9393-0303",
                MemberType.LOCAL, "testNickName1", "Oh1");
        Member member2 =  Member.ofLocalForTest(AccountRole.USER, "test2@test.com", encoder.encode("qweqwe123!"), "010-9393-0304",
                MemberType.LOCAL, "testNickName2", "Oh2");

        memberRepository.saveAll(List.of(member1, member2));

        Company company = Company.ofNewCompanyForTest("test3@test.com", "집다부셔", encoder.encode("qweqwe123!"),
                "010-2344-3333", "집다부셔버려", "1234", LocalDate.of(2020, 04, 30),
                "서울역 4번 출구", "다부셔버려");
        Company company2 = Company.ofNewCompanyForTest("test4@test.com", "집다부셔2", encoder.encode("qweqwe123!"),
                "010-2344-4444", "집다부셔버려2", "1234", LocalDate.of(2020, 04, 30),
                "서울역 4번 출구", "다부셔버려2");

        companyRepository.saveAll(List.of(company, company2));

        membershipTypeRepository.save(new MembershipType(100, MembershipName.BASIC));
        membershipTypeRepository.save(new MembershipType(150, MembershipName.PREMIUM));

        ConstructionType type1 = new ConstructionType("창호/샷시");
        ConstructionType type2 = new ConstructionType("도배/페인트");
        ConstructionType type3 = new ConstructionType("욕실");
        ConstructionType type4 = new ConstructionType("주방");
        ConstructionType type5 = new ConstructionType("장판");
        ConstructionType type6 = new ConstructionType("마루");
        ConstructionType type7 = new ConstructionType("전기/조명");
        ConstructionType type8 = new ConstructionType("필름");
        ConstructionType type9 = new ConstructionType("중문/도어");
        constructionTypeRepository.saveAll(List.of(
                type1, type2, type3, type4, type5, type6, type7, type8, type9
        ));

        BuildingType buildingType1 = new BuildingType("아파트");
        BuildingType buildingType2 = new BuildingType("빌라");
        BuildingType buildingType3 = new BuildingType("주택");
        BuildingType buildingType4 = new BuildingType("오피스텔");
        BuildingType buildingType5 = new BuildingType("상업");
        buildingTypeRepository.saveAll(List.of(
           buildingType1, buildingType2, buildingType3, buildingType4, buildingType5
        ));

    }
}
