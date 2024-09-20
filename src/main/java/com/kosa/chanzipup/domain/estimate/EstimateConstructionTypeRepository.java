package com.kosa.chanzipup.domain.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface EstimateConstructionTypeRepository extends JpaRepository<EstimateConstructionType, Long> {

    @Query("select et from EstimateConstructionType et left join fetch et.constructionType type where et.id in (:ids)")
    List<EstimateConstructionType> findAllByIdWithConstructionType(Collection<Long> ids);
}
