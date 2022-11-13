package teamproject.lam_server.controller.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.controller.common.Category;
import teamproject.lam_server.controller.common.DocsResponse;
import teamproject.lam_server.domain.city.constants.*;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewMenuGroup;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;
import teamproject.lam_server.domain.review.constants.ReviewSortType;
import teamproject.lam_server.domain.schedule.constants.ScheduleFilterType;
import teamproject.lam_server.domain.schedule.constants.ScheduleSearchType;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.global.enumMapper.EnumMapperType;
import teamproject.lam_server.paging.CustomPage;

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
                .transportGrade(getDocs(TransportGrade.values()))
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
