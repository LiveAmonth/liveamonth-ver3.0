package teamproject.lam_server.repository.interaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import teamproject.lam_server.repository.jdbc.member.MemberJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    Member toMember;
    Member fromMember;

    @BeforeAll
    void setUp() {
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
        toMember = memberRepository.save(createTo.toEntity(passwordEncoder));
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
        fromMember = memberRepository.save(createFrom.toEntity(passwordEncoder));
        List<MemberCreate> memberList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
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
    }

    @Test
    @DisplayName("상호작용(팔로우) 저장 성능 비교")
    void compare_follow_member() throws Exception {
        // given
        long startTime;
        long stopTime;
        long queryTime;
        long crudTime;

        // when
        InteractionRequest request = new InteractionRequest();
        request.setTo(toMember.getId());
        request.setFrom(fromMember.getId());

        startTime = System.currentTimeMillis();
        followRepository.follow(request);
        stopTime = System.currentTimeMillis();
        queryTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        Member to = memberRepository
                .findById(request.getFrom())
                .orElseThrow(MemberNotFound::new);
        Member from = memberRepository
                .findById(request.getTo())
                .orElseThrow(MemberNotFound::new);
        followRepository.save(Follower.builder().from(from).to(to).build());
        stopTime = System.currentTimeMillis();
        crudTime = stopTime - startTime;


        log.info("더 빠른 방식={}, 시간차={}", queryTime < crudTime ? "Native Query" : "CRUD", Math.abs(queryTime - crudTime));
    }

}
