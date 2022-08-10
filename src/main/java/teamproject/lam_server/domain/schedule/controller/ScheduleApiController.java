package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {
    private final ScheduleService scheduleApiService;

    @PostMapping()
    public ResponseEntity<?> addSchedule(ScheduleCreate request) {
        scheduleApiService.addSchedule(request);
        return CustomResponse.success(CREATE_SCHEDULE);
    }

    @PostMapping("/contents")
    public ResponseEntity<?> addScheduleContent(ScheduleContentCreate request){
        scheduleApiService.addScheduleContent(request);
        return CustomResponse.success(CREATE_SCHEDULE_CONTENT);
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Page<ScheduleCardResponse> result = scheduleApiService.search(cond, pageableDTO);
        return CustomResponse.success(READ_SCHEDULE, result);
    }

    @GetMapping("/{id}/contents")
    public ResponseEntity<?> getScheduleContents(@PathVariable Long id){
        List<ScheduleContentResponse> result = scheduleApiService.getScheduleContents(id);
        return CustomResponse.success(READ_SCHEDULE_CONTENT, result);
    }
}
