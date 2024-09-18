package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.review.ReviewConstructionType;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ConstructionTypeForReivewResponse {

    private List<String> reviewConstructionTypes;   // 엔티티에 시공분야가 list로 수정되어 있어서 이런식으로 수정함

    public ConstructionTypeForReivewResponse(List<ReviewConstructionType> constructionTypes) {
        this.reviewConstructionTypes = constructionTypes.stream().map(ReviewConstructionType::getTypeName).collect(Collectors.toList());
    }
}
