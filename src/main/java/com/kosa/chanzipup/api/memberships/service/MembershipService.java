package com.kosa.chanzipup.api.memberships.service;

import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.membership.MembershipRepository;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipService {
    private final CompanyRepository companyRepository;
    private final MembershipInternalRepository membershipInternalRepository;
    private final MembershipRepository membershipRepository;


}
