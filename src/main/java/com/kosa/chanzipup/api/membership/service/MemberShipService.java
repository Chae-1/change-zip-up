package com.kosa.chanzipup.api.membership.service;

import com.kosa.chanzipup.api.membership.controller.response.MemberShipResponse;
import com.kosa.chanzipup.domain.membership.MemberShip;
import com.kosa.chanzipup.domain.membership.MemberShipRepository;
import com.kosa.chanzipup.domain.membership.MemberShipType;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberShipService {

    private final MemberShipRepository memberShipRepository;

    //@PostConstruct
    public void init() {
        memberShipRepository.save(new MemberShip(100, MemberShipType.BASIC));
        memberShipRepository.save(new MemberShip(200, MemberShipType.PLATINUM));
    }

    public List<MemberShipResponse> getAllMemberShips() {
        return memberShipRepository.findAll()
                .stream()
                .map(MemberShipResponse::new)
                .toList();
    }
}
