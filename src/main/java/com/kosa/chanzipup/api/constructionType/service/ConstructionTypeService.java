package com.kosa.chanzipup.api.constructionType.service;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionTypeService {

    private final ConstructionTypeRepository constructionTypeRepository;

}
