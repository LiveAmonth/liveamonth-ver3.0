package teamproject.lam_server.repository.inquiry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
public class InquiryRepositoryTest {
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

    @Test
    @DisplayName("1:1문의 페이징 조회 성능 비교")
    void compare_get_inquiry_list() {
        // given
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
        Member savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));

        // when
        int count = 1000;
        List<InquiryCreate> inquiryCreates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inquiryCreates.add(InquiryCreate.builder()
                    .title("inquiry" + i)
                    .content("content")
                    .category(InquiryCategory.ETC.name())
                    .build());
        }
        inquiryJdbcRepository.batchInsert(inquiryCreates, savedMember);
        Pageable pageable = PageRequest.of(0, 10);

        // then
        // native query 방식
        long startTime = System.currentTimeMillis();
        Page<Inquiry> inquiries = inquiryTestRepository.getInquiries(savedMember.getId(), pageable);
        long stopTime = System.currentTimeMillis();
        long pagingTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        inquiryQueryRepository.getInquiries(savedMember.getId(), pageable);
        stopTime = System.currentTimeMillis();
        long indexTime = stopTime - startTime;

        log.info("== 결과(" + count + "건 기준) ==");
        log.info("기존 페이징 조회={}s", pagingTime);
        log.info("커버링 인덱스 조회={}s", indexTime);
        log.info("더 빠른 방식={}, 성능 개선율={}",
                pagingTime < indexTime ? "기존 페이징 조회" : "커버링 인덱스 조회",
                getPerformanceImprovementRate(pagingTime, indexTime));
    }
}


