package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleContentEditor;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleContentService;
import teamproject.lam_server.global.dto.CustomResponse;

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
            @RequestBody @Valid ScheduleContentEditor request) {
        scheduleContentService.addScheduleContent(scheduleId, request);
        return CustomResponse.success(CREATE_SCHEDULE_CONTENT);
    }


    @PatchMapping("/contents/{contentId}")
    public ResponseEntity<?> editScheduleContent(
            @PathVariable Long contentId,
            @RequestBody @Valid ScheduleContentEditor request) {
        scheduleContentService.editScheduleContent(contentId, request);
        return CustomResponse.success(UPDATE_SCHEDULE);
    }


    @DeleteMapping("/contents/{contentId}")
    public ResponseEntity<?> deleteScheduleContent(@PathVariable Long contentId) {
        scheduleContentService.deleteScheduleContent(contentId);
        return CustomResponse.success(DELETE_SCHEDULE_CONTENT);
    }

    @GetMapping("/{scheduleId}/contents")
    public ResponseEntity<?> getScheduleContents(@PathVariable Long scheduleId) {
        List<ScheduleContentResponse> result = scheduleContentService.getScheduleContents(scheduleId);
        return CustomResponse.success(READ_SCHEDULE_CONTENT, result);
    }
}
