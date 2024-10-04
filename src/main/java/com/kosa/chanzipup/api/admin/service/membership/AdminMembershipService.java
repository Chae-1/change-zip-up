package com.kosa.chanzipup.api.admin.service.membership;

import com.kosa.chanzipup.api.admin.controller.response.membership.MembershipCompanyResponse;
import com.kosa.chanzipup.api.admin.service.membership.query.AdminMembershipQueryRepository;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.membership.Membership;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMembershipService {

    private final AdminMembershipQueryRepository adminMembershipQueryRepository;

    private final MembershipTypeRepository membershipTypeRepository;

    public Page<List<MembershipCompanyResponse>> getAllMembershipCompanies(int pageSize, int pageNumber) {
        List<Membership> memberships = adminMembershipQueryRepository.getAllMembershipCompany();

        List<MembershipCompanyResponse> membershipCompanyResponses = memberships
                .stream()
                .map(MembershipCompanyResponse::new)
                .toList();

        return Page.of(membershipCompanyResponses, pageSize, pageNumber);
    }

    // 전체 멤버십 타입 조회
    public List<MembershipType> getAllMembershipTypes() {
        return membershipTypeRepository.findAll();
    }

    // 특정 멤버십 타입 조회
    public Optional<MembershipType> getMembershipTypeById(Long id) {
        return membershipTypeRepository.findById(id);
    }

    // 멤버십 타입 생성
    public MembershipType createMembershipType(MembershipType membershipType) {
        return membershipTypeRepository.save(membershipType);
    }

    // 멤버십 타입 수정
    public Optional<MembershipType> updateMembershipType(Long id, MembershipType updatedMembershipType) {
        return membershipTypeRepository.findById(id).map(membershipType -> {
            membershipType = updatedMembershipType;
            return membershipTypeRepository.save(membershipType);
        });
    }

    // 멤버십 타입 삭제
    public void deleteMembershipType(Long id) {
        membershipTypeRepository.deleteById(id);
    }

    @Transactional
    public String refundMembership(Long membershipId) {
        Membership membership = adminMembershipQueryRepository.findByIdWithPayment(membershipId);

        LocalDateTime refundDateTime = LocalDateTime.now();
        membership.refundPayment(refundDateTime);
        return membership.getPayment().getImpUid();
    }
    
    @Transactional
    public boolean updateMembershipPrice(Long membershipId, int price) {
        Membership membership = adminMembershipQueryRepository.findByIdWithPayment(membershipId);
        membership.updatePrice(price);
        return true;
    }
}


