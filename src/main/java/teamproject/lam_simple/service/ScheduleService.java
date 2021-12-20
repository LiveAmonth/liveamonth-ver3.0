package teamproject.lam_simple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.domain.Schedule;
import teamproject.lam_simple.domain.ScheduleContent;
import teamproject.lam_simple.dto.CalendarDTO;
import teamproject.lam_simple.repository.ScheduleContentRepository;
import teamproject.lam_simple.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;

    public List<Schedule> findTOP5Schedules() {
        return scheduleRepository.findTop5ByOrderByViewCountDesc();
    }

    public HashMap<String, Object> setCalendarDTOForScheduleList(List<Schedule> scheduleList, CalendarDTO calendarDTO){
        HashMap<String, Object> result = new HashMap<>();
        List<CalendarDTO> calendarDTOList = new ArrayList<>();
        List<List<CalendarDTO>> calendarDTODateList = new ArrayList<>();
        List<HashMap<String, Integer>> calendarDTOTodayInformationList = new ArrayList<>();
        for(Schedule schedule : scheduleList){
            calendarDTO = this.setManyContentsDate(schedule.getId(),calendarDTO); // 컨텐츠가 많은 달을 가져옴
            CalendarDTO calendarDto = this.showCalendar(schedule.getId(),calendarDTO);
            calendarDTOList.add(calendarDto);
            calendarDTODateList.add(calendarDto.getDateList());
            calendarDTOTodayInformationList.add((HashMap)calendarDto.getTodayInformation());
        }
        result.put("monthList", CategoryConstants.Month.values());
        result.put("calendarDTODateList", calendarDTODateList);
        result.put("todayInformations", calendarDTOTodayInformationList);
        return result;
    }

    private CalendarDTO setManyContentsDate(long id, CalendarDTO calendarDTO) {
        String year = scheduleRepository.getTopContentToYear(id);
        if (year != null) {
            calendarDTO.setYear(year);
        } else {
            calendarDTO.setYear("");
        }
        String month = scheduleRepository.getTopContentToMoth(id);
        if (month != null) {
            calendarDTO.setMonth(month);
        } else {
            calendarDTO.setMonth("");
        }
        return calendarDTO;
    }
    public CalendarDTO showCalendar(long id,CalendarDTO calendarDTO){
        Calendar cal = Calendar.getInstance();
        CalendarDTO calendarData;

        //검색 날짜
        if (calendarDTO.getDate().equals("") && calendarDTO.getMonth().equals("")) {
            calendarDTO = new CalendarDTO(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH)), String.valueOf(cal.get(Calendar.DATE)), null, null);
        }
        calendarDTO.setTodayInformation(calendarDTO.todayInformation(calendarDTO));
        calendarDTO.setDateList(new ArrayList<>());
        //검색 날짜 end
        List<ScheduleContent> ScheduleList = scheduleContentRepository.scheduleContentList(id, calendarDTO.getDbStartDate(),calendarDTO.getDbEndDate());

        //달력데이터에 넣기 위한 배열 추가
        ScheduleContent[][] scheduleDataArray = new ScheduleContent[32][4];
        if (ScheduleList.isEmpty() != true) {
            int j = 0;
            for (int i = 0; i < ScheduleList.size(); i++) {
                int date = Integer.parseInt(String.valueOf(ScheduleList.get(i).getScheduleContentDate()).substring(String.valueOf(ScheduleList.get(i).getScheduleContentDate()).length() - 2, String.valueOf(ScheduleList.get(i).getScheduleContentDate()).length()));
                if (i > 0) {
                    int dateBefore = Integer.parseInt(String.valueOf(ScheduleList.get(i - 1).getScheduleContentDate()).substring(String.valueOf(ScheduleList.get(i - 1).getScheduleContentDate()).length() - 2, String.valueOf(ScheduleList.get(i - 1).getScheduleContentDate()).length()));
                    if (dateBefore == date) {
                        j = j + 1;
                        scheduleDataArray[date][j] = ScheduleList.get(i);
                    } else {
                        j = 0;
                        scheduleDataArray[date][j] = ScheduleList.get(i);
                    }
                } else {
                    scheduleDataArray[date][j] = ScheduleList.get(i);
                }
            }
        }

        //실질적인 달력 데이터 리스트에 데이터 삽입 시작.
        //일단 시작 인덱스까지 아무것도 없는 데이터 삽입
        for (int i = 1; i < calendarDTO.getTodayInformation().get("start"); i++) {
            calendarData = new CalendarDTO(null, null, null, null, null);
            calendarDTO.getDateList().add(calendarData);
        }

        //날짜 삽입
        for (int i = calendarDTO.getTodayInformation().get("startDay"); i <= calendarDTO.getTodayInformation().get("endDay"); i++) {
            ScheduleContent[] scheduleDataArray3 = new ScheduleContent[4];
            scheduleDataArray3 = scheduleDataArray[i];

            if (i == calendarDTO.getTodayInformation().get("today")) {
                calendarData = new CalendarDTO(String.valueOf(calendarDTO.getYear()), String.valueOf(calendarDTO.getMonth()), String.valueOf(i), "today", scheduleDataArray3);
            } else {
                calendarData = new CalendarDTO(String.valueOf(calendarDTO.getYear()), String.valueOf(calendarDTO.getMonth()), String.valueOf(i), "normalDate", scheduleDataArray3);
            }
            calendarDTO.getDateList().add(calendarData);

        }

        //달력 빈 곳 빈 데이터로 삽입
        int index = 7 - calendarDTO.getDateList().size() % 7;
        if (calendarDTO.getDateList().size() % 7 != 0) {
            for (int i = 0; i < index; i++) {
                calendarData = new CalendarDTO(null, null, null, null, null);
                calendarDTO.getDateList().add(calendarData);
            }
        }
        return calendarDTO;
    }
}
