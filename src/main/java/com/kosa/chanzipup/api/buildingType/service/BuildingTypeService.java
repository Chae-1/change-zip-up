package com.kosa.chanzipup.api.buildingType.service;

import com.kosa.chanzipup.domain.buildingType.BuildingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingTypeService {

    private final BuildingTypeRepository buildingTypeRepository;
}
