package teamproject.lam_server.domain.inqiury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
}
