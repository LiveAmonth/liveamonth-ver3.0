package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.SimpleScheduleResponse;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.global.dto.ProfileCardResponse;

import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.READ_SCHEDULE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@Slf4j
public class ScheduleApiController {
    private final MemberRepository memberRepository;

    @GetMapping("/other")
    public ResponseEntity<?> getOtherSchedules() {
        return CustomResponse.success(READ_SCHEDULE, testData());
    }

    private List<SimpleScheduleResponse> testData() {
        Member member = memberRepository.findByLoginId("rbdus7174").orElse(null);
        List<SimpleScheduleResponse> result = new ArrayList<>();
        for (int i = 1; i < 6 + 1; i++) {
            SimpleScheduleResponse response = new SimpleScheduleResponse();
            response.setTitle(i + "번 스케줄 !!");
            response.setMember(ProfileCardResponse.of(member));
            response.setViewCount(i * 100);
            result.add(response);
        }
        return result;
    }
}
