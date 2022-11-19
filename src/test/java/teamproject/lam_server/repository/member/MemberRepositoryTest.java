package teamproject.lam_server.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.repository.jdbc.member.JdbcMemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
public class MemberRepositoryTest {
    @Autowired
    JdbcMemberRepository jdbcMemberRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("JDBC Bulk Insert 성능")
    void jdbc_bulk_insert(){
        // given
        List<MemberCreate> memberList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            MemberCreate memberCreate =
                    MemberCreate.builder()
                            .loginId("jdbc" + i)
                            .password("memberPassword1!")
                            .name("jdbc" + i)
                            .nickname("jdbc" + i)
                            .email("jdbc" + i + "@liveamonth.com")
                            .birth(LocalDate.now().minusDays(1))
                            .gender(GenderType.MALE.name())
                            .build();
            memberList.add(memberCreate);
        }

        // when
        long start = System.currentTimeMillis();
        jdbcMemberRepository.batchInsert(memberList);
        long end = System.currentTimeMillis();

        // then
        log.info("[jdbc bulk insert]경과 시간={}", end - start);
    }

}
