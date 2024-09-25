package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.account.member.MemberType;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EstimateRequestRepositoryTest {
    @Autowired
    EstimateRequestRepository estimateRequestRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BuildingTypeRepository buildingTypeRepository;
//
//    @Test
//    void findAllByRegDate() {
//        //        // given : 필요한 데이터는 무엇인데
//        Member member = Member.ofLocal(AccountRole.USER, "coguddlf1000@naver.com", "123123213213213!",
//                "010-1919-1919", MemberType.LOCAL, "nickName1", "hyeongil1");
//        memberRepository.save(member);
//
//        BuildingType type = new BuildingType("도배/시공");
//        buildingTypeRepository.save(type);
//
//        LocalDate now = LocalDate.now();
//
//        LocalDate oct = LocalDate.of(2024, 10, 11);
//        LocalDate nov = LocalDate.of(2024, 11, 11);
//
//        EstimateRequest request1 = EstimateRequest.builder()
//                .member(member)
//                .floor(1)
//                .measureDate(now)
//                .buildingType(type)
//                .budget("버젯1")
//                .schedule("스케쥴1")
//                .address("주소1")
//                .regDate(oct)
//                .build();
//
//        EstimateRequest request2 = EstimateRequest.builder()
//                .member(member)
//                .measureDate(now)
//                .buildingType(type)
//                .address("주소1")
//                .floor(1)
//                .schedule("스케쥴1")
//                .measureDate(now)
//                .budget("버젯1")
//                .regDate(oct)
//                .build();
//
//        EstimateRequest request3 = EstimateRequest.builder()
//                .member(member)
//                .floor(1)
//                .measureDate(now)
//                .buildingType(type)
//                .schedule("스케쥴1")
//                .address("주소1")
//                .regDate(oct)
//                .budget("버젯1")
//                .build();
//
//        EstimateRequest request4 = EstimateRequest.builder()
//                .member(member)
//                .measureDate(now)
//                .regDate(nov)
//                .floor(1)
//                .buildingType(type)
//                .schedule("스케쥴1")
//                .address("주소1")
//                .budget("버젯1")
//                .build();
//
//        EstimateRequest request5 = EstimateRequest.builder()
//                .member(member)
//                .measureDate(now)
//                .regDate(nov)
//                .floor(1)
//                .schedule("스케쥴1")
//                .buildingType(type)
//                .address("주소1")
//                .budget("버젯1")
//                .build();
//        estimateRequestRepository.saveAll(List.of(request1, request2, request3, request4, request5));
//
//        // when : 테스트할 메서드를 실행했을 때
//        List<EstimateRequest> estimateRequests = estimateRequestRepository.findAllByRegDate(nov);
//
//        // then : 어떤 결과가 나오나?
//        assertThat(estimateRequests).hasSize(2);
//
//    }
}

//@DataJpaTest
//class EstimateRequestRepositoryTest {
//    @Autowired
//    EstimateRequestRepository estimateRequestRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//
//    @org.junit.Test
//    @DisplayName("날짜를 기반으로 Estimate 요청 정보를 생성할 수 있다.")
//    void findAllByRegDate() {
//        // given : 필요한 데이터는 무엇인데
//        Member member = Member.ofLocal(AccountRole.USER, "coguddlf1000@naver.com", "123123213213213!",
//                "010-1919-1919", MemberType.LOCAL, "nickName1", "hyeongil1");
//        memberRepository.save(member);
//
//        LocalDate oct = LocalDate.of(2024, 10, 11);
//        LocalDate nov = LocalDate.of(2024, 11, 11);
//        EstimateRequest request1 = EstimateRequest.builder()
//                .member(member)
//                .regDate(oct)
//                .build();
//        EstimateRequest request2 = EstimateRequest.builder()
//                .member(member)
//                .regDate(oct)
//                .build();
//        EstimateRequest request3 = EstimateRequest.builder()
//                .member(member)
//                .regDate(oct)
//                .build();
//        EstimateRequest request4 = EstimateRequest.builder()
//                .member(member)
//                .regDate(nov)
//                .build();
//        EstimateRequest request5 = EstimateRequest.builder()
//                .member(member)
//                .regDate(nov)
//                .build();
//        estimateRequestRepository.saveAll(List.of(request1, request2, request3, request4, request5));
//
//        // when : 테스트할 메서드를 실행했을 때
//        List<EstimateRequest> estimateRequests = estimateRequestRepository.findAllByRegDate(nov);
//
//        // then : 어떤 결과가 나오나?
//        // 검증
//        assertThat(estimateRequests).hasSize(2);
//
//    }
//}
