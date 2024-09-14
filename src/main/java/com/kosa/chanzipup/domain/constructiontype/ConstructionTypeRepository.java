package com.kosa.chanzipup.domain.constructiontype;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructionTypeRepository extends JpaRepository<ConstructionType, Long> {
    List<ConstructionType> findByIdIn(List<Long> ids);
}
