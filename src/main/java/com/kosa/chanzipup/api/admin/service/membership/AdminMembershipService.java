package com.kosa.chanzipup.api.admin.service.membership;

import com.kosa.chanzipup.api.admin.service.membership.query.AdminMembershipQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMembershipService {

    private final AdminMembershipQueryRepository adminMembershipQueryRepository;

    public void getAllMembershipAccounts(Pageable pageable) {
        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

    }
}
