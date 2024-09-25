package com.kosa.chanzipup.api.admin.service.membership;

import com.kosa.chanzipup.api.admin.controller.response.membership.MembershipCompanyResponse;
import com.kosa.chanzipup.api.admin.service.membership.query.AdminMembershipQueryRepository;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.membership.Membership;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMembershipService {

    private final AdminMembershipQueryRepository adminMembershipQueryRepository;

    public Page<List<MembershipCompanyResponse>> getAllMembershipCompanies(Pageable pageable) {
        List<Membership> memberships = adminMembershipQueryRepository.getAllMembershipCompany();

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        List<MembershipCompanyResponse> membershipCompanyResponses = memberships
                .stream()
                .map(MembershipCompanyResponse::new)
                .toList();

        return Page.of(membershipCompanyResponses, pageSize, pageNumber);
    }
}
