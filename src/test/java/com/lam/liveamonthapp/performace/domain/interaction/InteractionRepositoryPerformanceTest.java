package com.lam.liveamonthapp.performace.domain.interaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.entity.member.Follower;
import com.lam.liveamonthapp.domain.interaction.repository.member.FollowRepository;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.exception.notfound.MemberNotFound;
import com.lam.liveamonthapp.bulk.interaction.InteractionJdbcRepository;
import com.lam.liveamonthapp.bulk.member.MemberJdbcRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.lam.liveamonthapp.restdocs.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
public class InteractionRepositoryPerformanceTest {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberJdbcRepository memberJdbcRepository;
    @Autowired
    InteractionJdbcRepository interactionJdbcRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상호작용(팔로우) 저장 성능 비교")
    void compare_follow_member() {
        // given
        int count = 50000;
        Member basicTo = createMember("basicTo");
        Member basicFrom = createMember("basicFrom");
        saveMemberWithBulk(count);
        saveFollowWithBulk(basicFrom.getId(), basicTo.getId(), count);
        em.clear();

        // when
        Member memberA;
        Member memberB;
        List<Long> nativeQueryTimes = new ArrayList<>();
        List<Long> dirtyCheckingTimes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            memberA = createMember("memberA" + i);
            memberB = createMember("memberB" + i);
            InteractionRequest request = new InteractionRequest();
            request.setFrom(memberB.getId());
            request.setTo(memberA.getId());

            long startTime = System.currentTimeMillis();
            followRepository.follow(request);
            long stopTime = System.currentTimeMillis();
            nativeQueryTimes.add(stopTime - startTime);
            em.clear();

            startTime = System.currentTimeMillis();
            Member from = memberRepository
                    .findByLoginId(memberA.getLoginId())
                    .orElseThrow(MemberNotFound::new);
            Member to = memberRepository
                    .findById(request.getFrom())
                    .orElseThrow(MemberNotFound::new);
            followRepository.save(Follower.builder().from(from).to(to).build());
            stopTime = System.currentTimeMillis();
            dirtyCheckingTimes.add(stopTime - startTime);
            em.clear();
        }

        double avgNativeQueryTime = nativeQueryTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        double avgDirtyCheckingTime = dirtyCheckingTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        log.info("== 결과(" + 10000 + "건 기준) ==");
        log.info("Native Query 저장 평균 속도={}ms", String.format("%.0f", avgNativeQueryTime));
        log.info("Dirty Checking 저장 평균 속도={}ms", String.format("%.0f", avgDirtyCheckingTime));
        log.info("높은 성능={}, 성능 개선율={}",
                avgNativeQueryTime < avgDirtyCheckingTime ? "Native Query" : "Dirty Checking",
                getPerformanceImprovementRate(avgNativeQueryTime, avgDirtyCheckingTime));
    }

    private Member createMember(String name) {
        MemberCreate createFrom =
                MemberCreate.builder()
                        .loginId(name)
                        .password("memberPassword1!")
                        .name(name)
                        .nickname(name)
                        .email(name + "@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        return memberRepository.save(createFrom.toEntity(passwordEncoder));
    }

    private void saveMemberWithBulk(int count) {
        List<MemberCreate> memberCreates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MemberCreate memberCreate =
                    MemberCreate.builder()
                            .loginId("member" + i)
                            .password("memberPassword1!")
                            .name("member" + i)
                            .nickname("member" + i)
                            .email("member" + i + "@liveamonth.com")
                            .birth(LocalDate.now().minusDays(1))
                            .gender(GenderType.MALE.name())
                            .build();
            memberCreates.add(memberCreate);
        }
        memberJdbcRepository.batchInsert(memberCreates);
    }

    private void saveFollowWithBulk(Long from, Long to, int count) {
        List<InteractionRequest> interactions = new ArrayList<>();
        for (int i = 10; i < count; i++) {
            InteractionRequest request = new InteractionRequest();
            request.setFrom(from);
            request.setTo((long) i);
            interactions.add(request);
            request.setFrom((long) i);
            request.setTo(to);
            interactions.add(request);
        }
        interactionJdbcRepository.batchInsert(interactions);
    }

}
