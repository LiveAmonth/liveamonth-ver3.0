package teamproject.lam_server.domain.inqiury.repository.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    @Query(value = "select i from Inquiry i join fetch i.member where i.member.id = :memberId order by i.id desc ",
            countQuery = "select m.numberOfInquiries from Member m where m.id = :memberId")
    Page<Inquiry> getInquiries(@Param("memberId") Long memberId, Pageable request);
}
