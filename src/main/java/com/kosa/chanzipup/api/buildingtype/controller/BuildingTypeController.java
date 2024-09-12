package com.kosa.chanzipup.api.buildingtype.controller;

import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buildingType")
public class BuildingTypeController {

    private final BuildingTypeRepository buildingTypeRepository;

    @GetMapping
    public List<BuildingType> getAll() {
        return buildingTypeRepository.findAll();  // 모든 BuildingType 데이터를 조회하여 반환
    }
}
