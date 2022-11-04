package teamproject.lam_server.controller.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    Map<String, String> genderType;
    Map<String, String> accountState;
    Map<String, String> inquiryCategory;
    Map<String, String> cityIntroCategory;
    Map<String, String> cityName;
    Map<String, String> transportCategory;
    Map<String, String> transportGrade;
    Map<String, String> monthCategory;
    Map<String, String> scheduleSearchType;
    Map<String, String> scheduleFilterType;
    Map<String, String> scheduleSortType;
    Map<String, String> reviewCategory;
    Map<String, String> reviewSearchGroup;
    Map<String, String> reviewSearchType;
    Map<String, String> reviewSortType;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public Category(Map<String, String> genderType,
                    Map<String, String> accountState,
                    Map<String, String> inquiryCategory,
                    Map<String, String> cityIntroCategory,
                    Map<String, String> cityName,
                    Map<String, String> transportCategory,
                    Map<String, String> transportGrade,
                    Map<String, String> monthCategory,
                    Map<String, String> scheduleSearchType,
                    Map<String, String> scheduleFilterType,
                    Map<String, String> scheduleSortType,
                    Map<String, String> reviewCategory,
                    Map<String, String> reviewSearchGroup,
                    Map<String, String> reviewSearchType,
                    Map<String, String> reviewSortType) {
        this.genderType = genderType;
        this.accountState = accountState;
        this.inquiryCategory = inquiryCategory;
        this.cityIntroCategory = cityIntroCategory;
        this.cityName = cityName;
        this.transportCategory = transportCategory;
        this.transportGrade = transportGrade;
        this.monthCategory = monthCategory;
        this.scheduleSearchType = scheduleSearchType;
        this.scheduleFilterType = scheduleFilterType;
        this.scheduleSortType = scheduleSortType;
        this.reviewCategory = reviewCategory;
        this.reviewSearchGroup = reviewSearchGroup;
        this.reviewSearchType = reviewSearchType;
        this.reviewSortType = reviewSortType;
    }
}
