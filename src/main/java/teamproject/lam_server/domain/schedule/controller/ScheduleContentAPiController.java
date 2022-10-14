package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleContentService;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleContentAPiController {
    private final ScheduleContentService scheduleContentService;

    @PostMapping("/{scheduleId}/contents")
    public ResponseEntity<?> addScheduleContent(
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleContentCreate request) {
        scheduleContentService.addScheduleContent(scheduleId, request);
        return CustomResponse.success(CREATE_SCHEDULE_CONTENT);
    }


    @PatchMapping("/contents/{contentId}")
    public ResponseEntity<?> editScheduleContent(
            @PathVariable Long contentId,
            @RequestBody @Valid ScheduleContentEdit request) {
        scheduleContentService.editScheduleContent(contentId, request);
        return CustomResponse.success(UPDATE_SCHEDULE);
    }


    @DeleteMapping("/contents/{contentId}")
    public ResponseEntity<?> deleteScheduleContent(@PathVariable Long contentId) {
        scheduleContentService.deleteScheduleContent(contentId);
        return CustomResponse.success(DELETE_SCHEDULE_CONTENT);
    }

    @GetMapping("/{scheduleId}/detail")
    public ResponseEntity<?> getScheduleContents(@PathVariable Long scheduleId) {
        List<ScheduleContentResponse> result = scheduleContentService.getScheduleContents(scheduleId);
        return CustomResponse.success(READ_SCHEDULE_CONTENT, result);
    }
}
