package teamproject.lam_server.app.schedule.service;

import teamproject.lam_server.app.schedule.domain.Schedule;
import teamproject.lam_server.app.schedule.dto.CalendarDTO;

import java.util.HashMap;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findTOP5Schedules();

    HashMap<String, Object> setCalendarDTOForScheduleList(List<Schedule> scheduleList, CalendarDTO calendarDTO);

    CalendarDTO showCalendar(long id,CalendarDTO calendarDTO);
}
