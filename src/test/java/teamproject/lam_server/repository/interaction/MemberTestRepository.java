package teamproject.lam_server.repository.interaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.List;

public interface MemberTestRepository extends JpaRepository<Member, Long> {

}
