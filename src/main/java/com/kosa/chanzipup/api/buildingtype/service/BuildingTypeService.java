package com.kosa.chanzipup.api.buildingtype.service;

import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingTypeService {

    private final BuildingTypeRepository buildingTypeRepository;
}
