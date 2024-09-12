package com.kosa.chanzipup.domain.account.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAll();

    Optional<Company> findByEmail(String email);
}
