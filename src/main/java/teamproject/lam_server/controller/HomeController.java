package teamproject.lam_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_server.constants.AttrConstants;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_server.constants.SessionConstants;
import teamproject.lam_server.domain.CityInfo;
import teamproject.lam_server.domain.Review;
import teamproject.lam_server.domain.Schedule;
import teamproject.lam_server.domain.User;
import teamproject.lam_server.dto.CalendarDTO;
import teamproject.lam_server.service.CityService;
import teamproject.lam_server.service.ReviewService;
import teamproject.lam_server.service.ScheduleService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CityService cityService;
    private final ScheduleService scheduleService;
    private final ReviewService reviewService;


    @GetMapping("city/cityInfos")
    public List<CityInfo> cityInfos() {
        return cityService.findCityInfoByCategory(CityInfoCategory.INTRO);
    }

    @GetMapping("month")
    public CategoryConstants.Month month() {
        return CategoryConstants.Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
    }

    @GetMapping("review/top5Reviews")
    public List<Review> otherReviewList() {
        return reviewService.findTOP5Reviews();
    }

    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser, Model model, CalendarDTO calendarDTO){
        if (loginUser != null) {
            model.addAttribute(SessionConstants.LOGIN_USER, loginUser);
        }
        List<Schedule> top5Schedules = scheduleService.findTOP5Schedules();
        HashMap<String, Object> result = scheduleService.setCalendarDTOForScheduleList(top5Schedules, calendarDTO);
        model.addAttribute(AttrConstants.TOP5_SCHEDULES, top5Schedules);
        model.addAttribute(AttrConstants.CALENDAR_DATA, result);

        return "main/home";
    }

}
