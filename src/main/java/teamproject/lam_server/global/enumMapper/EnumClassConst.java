package teamproject.lam_server.global.enumMapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumClassConst implements EnumMapperType {
    // Member
    GENDER_TYPE("GenderType", "성별"),
    ACCOUNT_STATE("AccountState", "계정 상태"),
    INQUIRY_CATEGORY("InquiryCategory", "문의 카테고리"),

    // City
    CITY_INTRO_CATEGORY("CityIntroCategory","도시 정보"),
    CITY_NAME("CityName","도시 이름"),
    TRANSPORT_CATEGORY("TransportCategory","대중 교통"),
    TRANSPORT_GRADE("TransportGrade","대중 교통 등급"),
    MONTH_CATEGORY("MonthCategory","월"),

    // Schedule
    SCHEDULE_SEARCH_TYPE("ScheduleSearchType", "스케줄 검색 타입"),
    SCHEDULE_FILTER_TYPE("ScheduleFilterType", "스케줄 필터 타입"),
    SCHEDULE_SORT_TYPE("ScheduleSortType", "스케줄 정렬 방식"),

    // Review
    REVIEW_CATEGORY("ReviewCategory","리뷰 게시판"),

    // Customer Center
    CUSTOMER_CENTER_MENU("CustomerCenterCategory","고객 센터");

    private final String className;
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
