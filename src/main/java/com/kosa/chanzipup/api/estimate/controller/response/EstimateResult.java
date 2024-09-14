package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class EstimateResult {

    // 업체에게 보내는 요청

    private Long companyId;

    private String username;

    private LocalDate requestDate; // Estimate 생성 날짜

    private List<String> constructionTypes;

    private String schedule;

    private String budget;

    // 업체와 계약하기 전에 주소를 보내주는 것이 맞는가?
    // 지미션팀은 대표님께서 개인 정보 보호에 대해 지적했음.
    // 처음엔 간단하게 생각했지만 지미션팀 얘기를 들어보니 맞는 말. 너무 당연함.
    // 업체에게 요청이 갈 때는 상세 주소를 빼고
    // 그 후에 계약이 될 때 주소를 보내주는 방법을 생각해볼 필요가 있어 보임.
    // 형일님께서 고민해주시고 결정해주세요!
    private String constructionAddress;

    private int floor;

    private BuildingType buildingType;


    @Builder
    public EstimateResult(Long companyId, String username, LocalDate requestDate,
                          List<String> constructionTypes,
                          String schedule, String budget, String constructionAddress, int floor,
                          BuildingType buildingType) {
        this.companyId = companyId;
        this.username = username;
        this.requestDate = requestDate;
        this.constructionTypes = constructionTypes;
        this.schedule = schedule;
        this.budget = budget;
        this.constructionAddress = constructionAddress;
        this.floor = floor;
        this.buildingType = buildingType;
    }

    public static EstimateResponse of(Company company, EstimateRequest request, Estimate estimate) {
        return EstimateResult.builder()
                .companyId(company.getId())
                .username(request.getMember().getNickName())
                .requestDate()
                .budget(request.getBudget())
                .build();  // 여기서부터 수정 필요
    }

    // 다음 순서
    // 1. 이 요청을 업체가 확인 후 승인하는 과정.
      // estimate 엔터티에 있는 status가 "대기중".
    // 2. 승인하고 견적(estimate)를 고객에게 보내줌.
      // 이 과정에서 견적(estimate) 엔터티에 데이터 저장.
      // estimate 엔터티에 있는 status가 "상담중" or "진행중".
      // 채팅이 확실하게 없다면 "진행중"
      // 한다면 "상담중"

    // 3. 고객이 업체가 보낸 견적을 확인 후 계약 진행?
      // 계약이 되면 estimate 엔터티에 있는 status가 "완료".

    // 제가 생각한 흐름은 이건데 형일님께서 생각해보시고 수정해주세요~
}
