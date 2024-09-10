package com.kosa.chanzipup.api.constructionType.service;

import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionTypeService {

    private final ConstructionTypeRepository constructionTypeRepository;

//    @PostConstruct
    public void init() {
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
    }

}
