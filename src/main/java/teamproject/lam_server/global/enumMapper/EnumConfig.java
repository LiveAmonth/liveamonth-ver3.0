package teamproject.lam_server.global.enumMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamproject.lam_server.domain.city.constants.*;
import teamproject.lam_server.domain.customercenter.constants.CustomerCenterMenu;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.MyPageMenu;
import teamproject.lam_server.domain.member.constants.MyPageSubCategory;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.schedule.constants.ScheduleSearchType;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;

import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        // Member
        enumMapper.put(GENDER_TYPE, GenderType.class);
        enumMapper.put(MY_PAGE_MENU, MyPageMenu.class);
        enumMapper.put(MY_PAGE_SUB_CATEGORY, MyPageSubCategory.class);
        enumMapper.put(ACCOUNT_STATE, AccountState.class);

        // City
        enumMapper.put(CITY_INTRO_CATEGORY, CityIntroCategory.class);
        enumMapper.put(CITY_NAME, CityName.class);
        enumMapper.put(TRANSPORT_CATEGORY, TransportCategory.class);
        enumMapper.put(TRANSPORT_GRADE, TransportGrade.class);
        enumMapper.put(MONTH_CATEGORY, MonthCategory.class);

        // Schedule
        enumMapper.put(SCHEDULE_SEARCH_TYPE, ScheduleSearchType.class);
        enumMapper.put(SCHEDULE_SORT_TYPE, ScheduleSortType.class);

        // Review
        enumMapper.put(REVIEW_CATEGORY, ReviewCategory.class);

        // Customer Center
        enumMapper.put(CUSTOMER_CENTER_MENU, CustomerCenterMenu.class);

        return enumMapper;
    }
}
