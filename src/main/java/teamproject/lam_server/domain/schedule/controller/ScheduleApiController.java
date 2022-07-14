package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.CreateSchedule;
import teamproject.lam_server.domain.schedule.dto.SimpleScheduleResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleApiService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.global.dto.ProfileCardResponse;

import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.READ_SCHEDULE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {
    private final MemberRepository memberRepository;
    private final ScheduleApiService scheduleApiService;
    @PostMapping("/{memberId}")
    public ResponseEntity<?> addSchedule(@PathVariable Long memberId, CreateSchedule request){
        scheduleApiService.addSchedule(memberId, request);
        return null;
    }


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
