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
    MY_PAGE_MENU("MyPageMenu", "내 정보"),
    MY_PAGE_SUB_CATEGORY("MyPageSubCategory", "내 정보 세부"),
    ACCOUNT_STATE("AccountState", "계정 상태"),

    // City
    CITY_INFO_CATEGORY("CityInfoCategory","도시 정보"),
    CITY_NAME("CityName","도시 이름"),
    TRANSPORT_CATEGORY("TransportCategory","대중 교통"),
    TRANSPORT_GRADE("TransportGrade","대중 교통 등급"),
    MONTH_CATEGORY("MonthCategory","월"),

    // Review
    REVIEW_CATEGORY("ReviewCategory","리뷰 게시판"),

    // Customer Center
    CUSTOMER_CENTER_MENU("CustomerCenterCategory","고객 센터");

    private String className;
    private String value;

    @Override
    public String getCode() {
        return name();
    }
}
