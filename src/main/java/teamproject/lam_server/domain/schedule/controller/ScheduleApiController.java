package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleRequest;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleDetailResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.PageableDTO;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@Slf4j
public class ScheduleApiController {
    private final ScheduleService scheduleApiService;

    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody CreateScheduleRequest request) {
        scheduleApiService.addSchedule(request);
        return CustomResponse.success(CREATE_SCHEDULE);
    }

    @PostMapping("/contents")
    public ResponseEntity<?> addScheduleContent(@RequestBody CreateScheduleContentRequest request) {
        scheduleApiService.addScheduleContent(request);
        return CustomResponse.success(CREATE_SCHEDULE_CONTENT);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getScheduleDetails(@RequestParam String nickname, @RequestParam String title) {
        ScheduleDetailResponse result = scheduleApiService.getScheduleDetails(nickname, title);
        return CustomResponse.success(READ_SCHEDULE_CONTENT, result);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Page<ScheduleCardResponse> result = scheduleApiService.search(cond, pageableDTO);
        return CustomResponse.success(READ_SCHEDULE, result);
    }
}
