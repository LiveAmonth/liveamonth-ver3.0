package com.lam.liveamonthapp.global.enumMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lam.liveamonthapp.domain.city.constants.*;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.domain.member.constants.AccountState;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.review.constants.ReviewCategory;
import com.lam.liveamonthapp.domain.review.constants.ReviewMenuGroup;
import com.lam.liveamonthapp.domain.review.constants.ReviewSearchType;
import com.lam.liveamonthapp.domain.review.constants.ReviewSortType;
import com.lam.liveamonthapp.domain.schedule.constants.ScheduleFilterType;
import com.lam.liveamonthapp.domain.schedule.constants.ScheduleSearchType;
import com.lam.liveamonthapp.domain.schedule.constants.ScheduleSortType;

import static com.lam.liveamonthapp.global.enumMapper.EnumClassConst.*;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        // Member
        enumMapper.put(GENDER_TYPE, GenderType.class);
        enumMapper.put(ACCOUNT_STATE, AccountState.class);
        enumMapper.put(INQUIRY_CATEGORY, InquiryCategory.class);
        enumMapper.put(CITY_INTRO_CATEGORY, CityIntroCategory.class);
        enumMapper.put(CITY_NAME, CityName.class);
        enumMapper.put(TRANSPORT_CATEGORY, TransportCategory.class);
        enumMapper.put(TRANSPORT_GRADE, TransportGrade.class);
        enumMapper.put(MONTH_CATEGORY, MonthCategory.class);
        enumMapper.put(SCHEDULE_SEARCH_TYPE, ScheduleSearchType.class);
        enumMapper.put(SCHEDULE_FILTER_TYPE, ScheduleFilterType.class);
        enumMapper.putMetaModelType(SCHEDULE_SORT_TYPE, ScheduleSortType.class);
        enumMapper.put(REVIEW_CATEGORY, ReviewCategory.class);
        enumMapper.put(REVIEW_MENU_GROUP, ReviewMenuGroup.class);
        enumMapper.put(REVIEW_SEARCH_TYPE, ReviewSearchType.class);
        enumMapper.putMetaModelType(REVIEW_SORT_TYPE, ReviewSortType.class);
        enumMapper.put(INTERACTION_TYPE, InteractionType.class);
        enumMapper.put(INTERACTION_STATE, InteractionState.class);
        enumMapper.put(COMMENT_TYPE, CommentType.class);

        return enumMapper;
    }
}
