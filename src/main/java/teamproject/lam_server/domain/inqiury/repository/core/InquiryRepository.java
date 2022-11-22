package teamproject.lam_server.domain.inqiury.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
