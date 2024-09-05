package com.kosa.chanzipup.api.membership.service;

import com.kosa.chanzipup.domain.membership.MemberShip;
import com.kosa.chanzipup.domain.membership.MemberShipRepository;
import com.kosa.chanzipup.domain.membership.MemberShipType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberShipService {

    private final MemberShipRepository memberShipRepository;

//    @PostConstruct
    public void init() {
        memberShipRepository.save(new MemberShip(100, MemberShipType.BASIC));
        memberShipRepository.save(new MemberShip(200, MemberShipType.PLATINUM));
    }


}
