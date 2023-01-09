package com.lam.liveamonthapp.restdocs.controller.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lam.liveamonthapp.restdocs.controller.common.Category;
import com.lam.liveamonthapp.restdocs.controller.common.DocsResponse;
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
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;
import com.lam.liveamonthapp.paging.CustomPage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/docs")
    public DocsResponse<CustomPage<Category>> commons() {
        Category result = Category.testBuilder()
                .genderType(getDocs(GenderType.values()))
                .accountState(getDocs(AccountState.values()))
                .inquiryCategory(getDocs(InquiryCategory.values()))
                .cityIntroCategory(getDocs(CityIntroCategory.values()))
                .cityName(getDocs(CityName.values()))
                .transportCategory(getDocs(TransportCategory.values()))
                .monthCategory(getDocs(MonthCategory.values()))
                .scheduleSearchType(getDocs(ScheduleSearchType.values()))
                .scheduleFilterType(getDocs(ScheduleFilterType.values()))
                .scheduleSortType(getDocs(ScheduleSortType.values()))
                .reviewCategory(getDocs(ReviewCategory.values()))
                .reviewSearchGroup(getDocs(ReviewMenuGroup.values()))
                .reviewSearchType(getDocs(ReviewSearchType.values()))
                .reviewSortType(getDocs(ReviewSortType.values()))
                .interactionType(getDocs(InteractionType.values()))
                .interactionState(getDocs(InteractionState.values()))
                .commentType(getDocs(CommentType.values()))
                .build();

        Page<Category> categories = new PageImpl<>(
                List.of(result),
                PageRequest.of(0, 10),
                1
        );

        return DocsResponse.success("모든 카테고리 조회",
                CustomPage.<Category>builder()
                        .page(categories)
                        .build());
    }

    private Map<String, String> getDocs(EnumMapperType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumMapperType::getCode, EnumMapperType::getValue));
    }
}
