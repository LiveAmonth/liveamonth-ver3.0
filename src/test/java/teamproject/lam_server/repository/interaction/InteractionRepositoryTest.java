package teamproject.lam_server.repository.interaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.member.Follower;
import teamproject.lam_server.domain.interaction.repository.member.FollowRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.jdbc.member.MemberJdbcRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
public class InteractionRepositoryTest {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberJdbcRepository memberJdbcRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상호작용(팔로우) 저장 성능 비교")
    void compare_follow_member(){
        // given
        MemberCreate createTo =
                MemberCreate.builder()
                        .loginId("toMember")
                        .password("memberPassword1!")
                        .name("toMember")
                        .nickname("toMember")
                        .email("toMember@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        Member toMember = memberRepository.save(createTo.toEntity(passwordEncoder));
        MemberCreate createFrom =
                MemberCreate.builder()
                        .loginId("fromMember")
                        .password("memberPassword1!")
                        .name("fromMember")
                        .nickname("fromMember")
                        .email("fromMember@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        Member fromMember = memberRepository.save(createFrom.toEntity(passwordEncoder));
        int count = 100000;
        List<MemberCreate> memberList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MemberCreate memberCreate =
                    MemberCreate.builder()
                            .loginId("member" + i)
                            .password("memberPassword1!")
                            .name("memberName" + i)
                            .nickname("memberNickname" + i)
                            .email("member" + i + "@liveamonth.com")
                            .birth(LocalDate.now().minusDays(1))
                            .gender(GenderType.MALE.name())
                            .build();
            memberList.add(memberCreate);
        }
        memberJdbcRepository.batchInsert(memberList);
        em.clear();

        // when
        InteractionRequest request = new InteractionRequest();
        request.setTo(toMember.getId());
        request.setFrom(fromMember.getId());

        long startTime = System.currentTimeMillis();
        followRepository.follow(request);
        long stopTime = System.currentTimeMillis();
        long queryTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        Member to = memberRepository
                .findById(request.getFrom())
                .orElseThrow(MemberNotFound::new);
        Member from = memberRepository
                .findById(request.getTo())
                .orElseThrow(MemberNotFound::new);
        followRepository.save(Follower.builder().from(from).to(to).build());
        stopTime = System.currentTimeMillis();
        long crudTime = stopTime - startTime;

        log.info("== 결과(" + count + "건 기준) ==");
        log.info("dirty checking={}ms", crudTime);
        log.info("native query={}ms", queryTime);
        log.info("더 빠른 방식={}, 성능 개선율={}",
                crudTime < queryTime ? "dirty checking" : "native query",
                getPerformanceImprovementRate(crudTime, queryTime));
    }

}
