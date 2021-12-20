package teamproject.lam_simple.dto;

import lombok.Getter;
import lombok.Setter;
import teamproject.lam_simple.domain.ScheduleContent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CalendarDTO {
    private String year = "";
    private String month = "";
    private String date = "";
    private String value = "";

    private String dbStartDate = "";
    private String dbEndDate = "";
    private ScheduleContent[] scheduleDataArray = new ScheduleContent[4];

    private Map<String, Integer> todayInformation;
    private List<CalendarDTO> dateList;

    public CalendarDTO() {
    }

    // 날짜에 관련된 달력정보를 가지는 메서드
    public Map<String, Integer> todayInformation(CalendarDTO calendarDTO) {
        // 날짜 캘린더 함수에 삽입.
        Map<String, Integer> todayData = new HashMap<String, Integer>();
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(calendarDTO.getYear()), Integer.parseInt(calendarDTO.getMonth()), 1);

        int startDay = cal.getMinimum(Calendar.DATE);
        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int start = cal.get(Calendar.DAY_OF_WEEK);

        Calendar todayCal = Calendar.getInstance();
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat msdf = new SimpleDateFormat("M");

        int todayYear = Integer.parseInt(ysdf.format(todayCal.getTime()));
        int todayMonth = Integer.parseInt(msdf.format(todayCal.getTime()));

        int searchYear = Integer.parseInt(calendarDTO.getYear());
        int searchMonth = Integer.parseInt(calendarDTO.getMonth());

        int today = -1;
        if (todayYear == searchYear && todayMonth == searchMonth) {
            SimpleDateFormat dsdf = new SimpleDateFormat("dd");
            today = Integer.parseInt(dsdf.format(todayCal.getTime()));
        }

        searchMonth = searchMonth - 1;

        Map<String, Integer> beforeAfterCalendar = beforeAfterCalendar(searchYear, searchMonth);

        //날짜 관련
//        System.out.println("search_month : " + searchMonth);
        // 캘린더 함수 end
        todayData.put("start", start);
        todayData.put("startDay", startDay);
        todayData.put("endDay", endDay);
        todayData.put("today", today);
        todayData.put("searchYear", searchYear);
        todayData.put("searchMonth", searchMonth + 1);
        todayData.put("beforeYear", beforeAfterCalendar.get("beforeYear"));
        todayData.put("beforeMonth", beforeAfterCalendar.get("beforeMonth"));
        todayData.put("afterYear", beforeAfterCalendar.get("afterYear"));
        todayData.put("afterMonth", beforeAfterCalendar.get("afterMonth"));


        this.dbStartDate = String.valueOf(searchYear) + "-" + String.valueOf(searchMonth + 1) + "-" + String.valueOf(startDay);
        this.dbEndDate = String.valueOf(searchYear) + "-" + String.valueOf(searchMonth + 1) + "-" + String.valueOf(endDay);
        return todayData;
    }

    //이전달 다음달 및 이전년도 다음년도
    private Map<String, Integer> beforeAfterCalendar(int searchYear, int searchMonth) {
        Map<String, Integer> beforeAfterData = new HashMap<String, Integer>();
        int beforeYear = searchYear;
        int beforeMonth = searchMonth - 1;
        int afterYear = searchYear;
        int afterMonth = searchMonth + 1;

        if (beforeMonth < 0) {
            beforeMonth = 11;
            beforeYear = searchYear - 1;
        }

        if (afterMonth > 11) {
            afterMonth = 0;
            afterYear = searchYear + 1;
        }

        beforeAfterData.put("beforeYear", beforeYear);
        beforeAfterData.put("beforeMonth", beforeMonth);
        beforeAfterData.put("afterYear", afterYear);
        beforeAfterData.put("afterMonth", afterMonth);

        return beforeAfterData;
    }

    // 스케줄 사용시 사용될 생성자
    public CalendarDTO(String year, String month, String date, String value, ScheduleContent[] scheduleDataArray) {
        if ((month != null && month != "") && (date != null && date != "")) {
            this.year = year;
            this.month = month;
            this.date = date;
            this.value = value;
            this.scheduleDataArray = scheduleDataArray;
        }
    }

    @Override
    public String toString() {
        return "DateData [year=" + year + ", month=" + month + ", date=" + date + ", value=" + value + "]";
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDbStartDate() {
        return dbStartDate;
    }

    public void setDbStartDate(String dbStartDate) {
        this.dbStartDate = dbStartDate;
    }

    public String getDbEndDate() {
        return dbEndDate;
    }

    public void setDbEndDate(String dbEndDate) {
        this.dbEndDate = dbEndDate;
    }

    public ScheduleContent[] getScheduleDataArray() {
        return scheduleDataArray;
    }

    public void setScheduleDataArray(ScheduleContent[] scheduleDataArray) {
        this.scheduleDataArray = scheduleDataArray;
    }

    public Map<String, Integer> getTodayInformation() {
        return todayInformation;
    }

    public void setTodayInformation(Map<String, Integer> todayInformation) {
        this.todayInformation = todayInformation;
    }

    public List<CalendarDTO> getDateList() {
        return dateList;
    }

    public void setDateList(List<CalendarDTO> dateList) {
        this.dateList = dateList;
    }
}


