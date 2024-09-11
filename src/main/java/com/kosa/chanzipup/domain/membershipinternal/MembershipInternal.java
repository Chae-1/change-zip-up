package com.kosa.chanzipup.domain.membershipinternal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MembershipInternal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MembershipType type;

    private int price;

    public MembershipInternal(int price,
                              MembershipType type) {
        this.type = type;
        this.price = price;
    }
}
