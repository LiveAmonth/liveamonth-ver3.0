package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_simple.constants.AttrConstants;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_simple.constants.SessionConstants;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.domain.Review;
import teamproject.lam_simple.domain.Schedule;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.dto.CalendarDTO;
import teamproject.lam_simple.service.CityService;
import teamproject.lam_simple.service.ReviewService;
import teamproject.lam_simple.service.ScheduleService;
import teamproject.lam_simple.service.UserService;

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
