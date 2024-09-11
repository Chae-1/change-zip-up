package com.kosa.chanzipup.api.memberships.service;



import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import com.kosa.chanzipup.domain.membership.MembershipName;
import com.kosa.chanzipup.api.member.controller.response.MembershipTypeResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTypeService {

    private final MembershipTypeRepository membershipTypeRepository;

    // query
    public List<MembershipTypeResponse> getAllMemberShips() {
        return membershipTypeRepository.findAll()
                .stream()
                .map(MembershipTypeResponse::new)
                .toList();
    }


}
