package com.kosa.chanzipup.api.membershipinternal.service;

import com.kosa.chanzipup.api.membershipinternal.controller.response.MemberShipInternalResponse;
import com.kosa.chanzipup.domain.membershipinternal.MemberShipType;

import java.util.List;

import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternalRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberShipInternalService {

    private final MembershipInternalRepository membershipInternalRepository;

    public List<MemberShipInternalResponse> getAllMemberShips() {
        return membershipInternalRepository.findAll()
                .stream()
                .map(MemberShipInternalResponse::new)
                .toList();
    }


    public void init() {
        membershipInternalRepository.save(new MembershipInternal(100, MemberShipType.BASIC));
        membershipInternalRepository.save(new MembershipInternal(150, MemberShipType.PLATINUM));
    }
}
