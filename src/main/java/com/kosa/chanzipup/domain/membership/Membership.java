package com.kosa.chanzipup.domain.membership;

import com.kosa.chanzipup.domain.BaseEntity;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_internal_id")
    private MembershipInternal membershipInternal;

//    @OneToOne
//    @JoinColumn(name = "company_id")
//    private Company company

}
