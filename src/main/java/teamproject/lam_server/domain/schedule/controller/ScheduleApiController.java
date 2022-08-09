package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.schedule.dto.CreateSchedule;
import teamproject.lam_server.domain.schedule.service.ScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {
    private final ScheduleService scheduleApiService;

    @PostMapping("/{memberId}")
    public ResponseEntity<?> addSchedule(@PathVariable Long memberId, CreateSchedule request) {
        scheduleApiService.addSchedule(memberId, request);
        return null;
    }
}
