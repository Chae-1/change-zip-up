package com.kosa.chanzipup.api.constructionType.controller;

import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/constructionType")
public class ConstructionTypeController {

    private final ConstructionTypeRepository constructionTypeRepository;

    @GetMapping
    public List<ConstructionType> getAll() {
        return constructionTypeRepository.findAll();  // 모든 ConstructionType 데이터를 조회하여 반환
    }
}
