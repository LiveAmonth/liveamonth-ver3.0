package teamproject.lam_server.repository.inquiry;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.repository.query.InquiryQueryRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.jdbc.inquiry.InquiryJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class InquiryRepositoryTest {
    Member savedMember;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    InquiryJdbcRepository inquiryJdbcRepository;

    @Autowired
    InquiryTestRepository inquiryTestRepository;
    @Autowired
    InquiryQueryRepository inquiryQueryRepository;

    @BeforeAll
    void setUp() {
        MemberCreate memberCreate =
                MemberCreate.builder()
                        .loginId("member")
                        .password("memberPassword1!")
                        .name("memberName")
                        .nickname("memberNickname")
                        .email("member@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    @Test
    @DisplayName("1:1문의 페이징 조회 성능 비교")
    void compare_get_inquiry_list(){
        // given
        List<String> methods = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 50;
        long startTime;
        long stopTime;
        long nativeQueryTime;
        long querydslTime;

        // when
        List<InquiryCreate> inquiryCreates = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            inquiryCreates.add(InquiryCreate.builder()
                    .title("inquiry" + i)
                    .content("content")
                    .category(InquiryCategory.ETC.name())
                    .build());
        }
        inquiryJdbcRepository.batchInsert(inquiryCreates, savedMember);
        Pageable pageable = PageRequest.of(0, 10);

        // then
        for (int i = 0; i < iterations; i++) {
            // native query 방식
            startTime = System.currentTimeMillis();
            Page<Inquiry> listByNativeQuery = inquiryTestRepository.getInquiries(savedMember.getId(), pageable);
            stopTime = System.currentTimeMillis();
            nativeQueryTime = stopTime - startTime;

            startTime = System.currentTimeMillis();
            Page<InquiryListResponse> listByQuerydsl = inquiryQueryRepository.getInquiries(savedMember.getId(), pageable);
            stopTime = System.currentTimeMillis();
            querydslTime = stopTime - startTime;

            Assertions.assertThat(listByNativeQuery.getContent().size()).isEqualTo(listByQuerydsl.getContent().size());
            methods.add(nativeQueryTime < querydslTime ? "Native Query" : "Querydsl");
            times.add(Math.abs(nativeQueryTime - querydslTime));
        }

        log.info("============== 결과 ================");
        for (int i = 0; i < iterations; i++) {
            log.info("더 빠른 방식={}, 시간차={}", methods.get(i), times.get(i));
        }
    }
}


