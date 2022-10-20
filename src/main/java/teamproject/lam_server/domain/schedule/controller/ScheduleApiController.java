package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleEdit;
import teamproject.lam_server.domain.schedule.dto.response.MyScheduleResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.EditableScheduleResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleService;
import teamproject.lam_server.global.dto.response.CountResponse;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {
    private final ScheduleService scheduleApiService;

    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody @Valid ScheduleCreate request) {
        scheduleApiService.addSchedule(request);
        return CustomResponse.success(CREATE_SCHEDULE);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<?> editSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleEdit request) {
        scheduleApiService.editSchedule(scheduleId, request);
        return CustomResponse.success(UPDATE_SCHEDULE);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleApiService.deleteSchedule(scheduleId);
        return CustomResponse.success(DELETE_SCHEDULE);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        CustomPage<ScheduleCardResponse> result = scheduleApiService.search(cond, pageableDTO);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{loginId}/edit")
    public ResponseEntity<?> getMySchedulesToEdit(@PathVariable String loginId) {
        List<EditableScheduleResponse> result = scheduleApiService.getEditableSchedules(loginId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{loginId}")
    public ResponseEntity<?> getMySchedules(
            @PathVariable String loginId,
            @RequestParam(required = false) Integer size,
            @RequestParam(name = "last_id", required = false) Long lastId) {
        List<MyScheduleResponse> result = scheduleApiService.getMySchedules(loginId, size, lastId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{loginId}/followed")
    public ResponseEntity<?> searchFollowedSchedules(
            @PathVariable String loginId,
            @RequestParam(required = false) Integer size,
            @RequestParam(name = "last_id", required = false) Long lastId) {
        List<ScheduleCardResponse> result = scheduleApiService.getFollowedSchedules(loginId, size, lastId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/count/{loginId}/followed")
    public ResponseEntity<?> getFollowedScheduleCounts(@PathVariable String loginId) {
        CountResponse result = scheduleApiService.getNumberOfFollowedPosts(loginId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }
}
