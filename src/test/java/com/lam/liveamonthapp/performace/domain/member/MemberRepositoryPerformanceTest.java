package com.lam.liveamonthapp.performace.domain.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.domain.member.repository.query.MemberQueryRepository;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.bulk.member.MemberJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.lam.liveamonthapp.domain.member.entity.QMember.member;
import static com.lam.liveamonthapp.restdocs.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
public class MemberRepositoryPerformanceTest {
    @Autowired
    MemberJdbcRepository memberJdbcRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberQueryRepository memberQueryRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("JDBC Bulk Insert 성능")
    void jdbc_bulk_insert() {
        // given
        List<MemberCreate> memberList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
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
        memberJdbcRepository.batchInsert(memberList);
        long end = System.currentTimeMillis();

        // then
        log.info("[jdbc bulk insert]경과 시간={}", end - start);
    }

    @Test
    @DisplayName("exists 쿼리 성능 비교")
    void compare_exists_query() {
        // given
        int count = 10000;
        List<MemberCreate> memberList = new ArrayList<>();
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
            memberList.add(memberCreate);
        }
        memberJdbcRepository.batchInsert(memberList);

        // when
        long startTime = System.currentTimeMillis();
        Boolean resultA = memberRepository.existsByLoginId("member0");
        long stopTime = System.currentTimeMillis();
        long springDataJPA = stopTime - startTime;

        startTime = System.currentTimeMillis();
        Integer fetchOne = queryFactory.selectOne()
                .from(member)
                .where(member.loginId.eq("member0"))
                .fetchFirst();
        Boolean resultB = fetchOne != null;
        stopTime = System.currentTimeMillis();
        long querydsl = stopTime - startTime;

        // then
        log.info("============== 결과(" + count + "건 기준) ================");
        Assertions.assertEquals(resultA, resultB);
        log.info("Spring data jpa 조회={}ms", springDataJPA);
        log.info("querydsl 조회={}ms", querydsl);
        log.info("더 빠른 방식={}, 성능 개선율={}", springDataJPA < querydsl ? "spring Data JPA" : "querydsl", getPerformanceImprovementRate(springDataJPA, querydsl));
    }

}
