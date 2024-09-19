package com.kosa.chanzipup.api.estimate.service.query;

import com.kosa.chanzipup.api.estimate.controller.response.EstimateConstructionTypeResponse;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestResponse;
import com.kosa.chanzipup.domain.buildingtype.QBuildingType;
import com.kosa.chanzipup.domain.estimate.EstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import com.kosa.chanzipup.domain.estimate.QEstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.QEstimateRequest;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimateConstructionType.estimateConstructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimateRequest.estimateRequest;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstimateRequestQueryService {
    private final JPAQueryFactory factory;


    public List<EstimateRequestResponse> estimateRequestResponses() {

        StringExpression fullAddress = estimateRequest
                .address.concat(" ")
                .concat(estimateRequest.detailedAddress).as("fullAddress");

        return factory.select(estimateRequest)
                .from(estimateRequest)
                .leftJoin(estimateRequest.member, member)
                .leftJoin(estimateRequest.buildingType, buildingType)
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType)
                .transform(groupBy(estimateRequest.id).list(constructor(EstimateRequestResponse.class,
                        estimateRequest.id.as("requestId"), fullAddress, estimateRequest.floor,
                        estimateRequest.budget, estimateRequest.schedule, member.nickName,
                        estimateRequest.buildingType.name.as("buildingTypeName"),
                        estimateRequest.regDate,
                        list(constructor(EstimateConstructionTypeResponse.class, estimateConstructionType.constructionType.name)))));
    }

}
