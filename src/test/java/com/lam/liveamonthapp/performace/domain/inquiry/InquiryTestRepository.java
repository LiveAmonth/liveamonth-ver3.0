package com.lam.liveamonthapp.performace.domain.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lam.liveamonthapp.domain.inqiury.entity.Inquiry;

public interface InquiryTestRepository extends JpaRepository<Inquiry, Long> {

    @Query(value = "select i from Inquiry i join fetch i.member where i.member.id = :memberId order by i.id desc ",
            countQuery = "select m.numberOfInquiries from Member m where m.id = :memberId")
    Page<Inquiry> getInquiries(@Param("memberId") Long memberId, Pageable request);
}
