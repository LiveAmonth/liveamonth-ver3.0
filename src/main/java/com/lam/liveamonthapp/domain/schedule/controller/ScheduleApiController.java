package com.lam.liveamonthapp.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.schedule.dto.condition.ScheduleSearchCond;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleCreate;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleEdit;
import com.lam.liveamonthapp.domain.schedule.dto.response.MyScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.ScheduleCardResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.EditableScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.service.ScheduleService;
import com.lam.liveamonthapp.global.dto.response.CountResponse;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import javax.validation.Valid;
import java.util.List;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {
    private final ScheduleService scheduleApiService;

    @PostMapping("/{login_id}")
    public ResponseEntity<?> addSchedule(
            @PathVariable("login_id") String loginId,
            @RequestBody @Valid ScheduleCreate request) {
        PostIdResponse result = scheduleApiService.addSchedule(loginId, request);
        return CustomResponse.success(CREATE_SCHEDULE, result);
    }

    @PatchMapping("/{schedule_id}")
    public ResponseEntity<?> editSchedule(
            @PathVariable("schedule_id") Long scheduleId,
            @RequestBody @Valid ScheduleEdit request) {
        scheduleApiService.editSchedule(scheduleId, request);
        return CustomResponse.success(UPDATE_SCHEDULE);
    }

    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable("schedule_id") Long scheduleId) {
        scheduleApiService.deleteSchedule(scheduleId);
        return CustomResponse.success(DELETE_SCHEDULE);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        CustomPage<ScheduleCardResponse> result = scheduleApiService.search(cond, pageableDTO);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/best")
    public ResponseEntity<?> getBestSchedules() {
        List<ScheduleCardResponse> result = scheduleApiService.getBestSchedules();
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{login_id}/edit")
    public ResponseEntity<?> getMySchedulesToEdit(@PathVariable("login_id") String loginId) {
        List<EditableScheduleResponse> result = scheduleApiService.getEditableSchedules(loginId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{login_id}")
    public ResponseEntity<?> getMySchedules(
            @PathVariable("login_id") String loginId,
            @RequestParam(required = false) Integer size,
            @RequestParam(name = "last_id", required = false) Long lastId) {
        List<MyScheduleResponse> result = scheduleApiService.getMySchedules(loginId, size, lastId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/list/{login_id}/followed")
    public ResponseEntity<?> searchFollowedSchedules(
            @PathVariable("login_id") String loginId,
            @RequestParam(required = false) Integer size,
            @RequestParam(name = "last_id", required = false) Long lastId) {
        List<ScheduleCardResponse> result = scheduleApiService.getFollowedSchedules(loginId, size, lastId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/count/{login_id}/followed")
    public ResponseEntity<?> getFollowedScheduleCounts(@PathVariable("login_id") String loginId) {
        CountResponse result = scheduleApiService.getNumberOfFollowedPosts(loginId);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @PatchMapping("/{schedule_id}/count-up")
    public ResponseEntity<?> viewCountUp(@PathVariable("schedule_id") Long scheduleId) {
        scheduleApiService.viewCountUp(scheduleId);
        return CustomResponse.success(READ_SCHEDULE);
    }
}
