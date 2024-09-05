package com.kosa.chanzipup.domain.membership;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberShip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @Enumerated(EnumType.STRING)
    private MemberShipType type;

    public MemberShip(int price, MemberShipType type) {
        this.price = price;
        this.type = type;
    }
}
