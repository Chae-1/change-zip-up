package com.kosa.chanzipup.domain.account.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAll();

    Optional<Company> findById(Long id);

    Optional<Company> findByEmail(String email);

    Optional<Company> findByCompanyName(String companyName);

    @Query("select c from Company c left join fetch c.constructionTypes type " +
            "left join fetch type.constructionType t " +
            "where c.email = :email")
    Optional<Company> findByEmailWithAll(@Param("email") String email);
}
