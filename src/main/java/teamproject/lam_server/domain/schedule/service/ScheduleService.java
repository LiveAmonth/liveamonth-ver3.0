package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.dto.CalendarDTO;

import java.util.HashMap;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findTOP5Schedules();

    HashMap<String, Object> setCalendarDTOForScheduleList(List<Schedule> scheduleList, CalendarDTO calendarDTO);

    CalendarDTO showCalendar(long id,CalendarDTO calendarDTO);
}
