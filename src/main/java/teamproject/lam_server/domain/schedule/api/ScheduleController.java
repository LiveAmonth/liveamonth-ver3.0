package teamproject.lam_server.domain.schedule.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.constants.AttrConstants;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.dto.CalendarDTO;
import teamproject.lam_server.domain.schedule.service.ScheduleService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/top-five")
    public HashMap<String, Object> getTop5ScheduleList(CalendarDTO calendarDTO) {
        HashMap<String, Object> result = new HashMap<>();
        List<Schedule> top5Schedules = scheduleService.findTOP5Schedules();
        result.put(AttrConstants.TOP5_SCHEDULES, top5Schedules);
        result.put(AttrConstants.CALENDAR_DATA, scheduleService.setCalendarDTOForScheduleList(top5Schedules, calendarDTO));
        return result;
    }
}
