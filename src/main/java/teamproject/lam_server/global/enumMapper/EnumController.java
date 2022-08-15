package teamproject.lam_server.global.enumMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.global.dto.CustomResponse;

import static teamproject.lam_server.global.constants.ResponseMessage.READ_CATEGORY;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;

/**
 * {@link EnumClassConst}에 있는 Category 조희
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class EnumController {

    private final EnumMapper enumMapper;

    /**
     * 성별 타입
     */
    @GetMapping("/gender-type")
    public ResponseEntity<?> getGenderType() {
        return getCategories(GENDER_TYPE);
    }

    /**
     * 계정 상태
     */
    @GetMapping("/account-state")
    public ResponseEntity<?> getAccountState() {
        return getCategories(ACCOUNT_STATE);
    }

    /**
     * 도시 이름
     */
    @GetMapping("/city/names")
    public ResponseEntity<?> getCityName() {
        return getCategories(CITY_NAME);
    }

    /**
     * 도시 소개 카테고리
     */
    @GetMapping("/city/intro")
    public ResponseEntity<?> getCityIntro() {
        return getCategories(CITY_INTRO_CATEGORY);
    }

    /**
     * 교통 수단 카테고리
     */
    @GetMapping("/transport")
    public ResponseEntity<?> getTransport() {
        return getCategories(TRANSPORT_CATEGORY);
    }

    /**
     * 교통 등급
     */
    @GetMapping("/transport/grade")
    public ResponseEntity<?> getTransportGrade() {
        return getCategories(TRANSPORT_GRADE);
    }

    /**
     * 월 카테고리
     */
    @GetMapping("/month")
    public ResponseEntity<?> getMonth() {
        return getCategories(MONTH_CATEGORY);
    }

    /**
     * 리뷰 카테고리
     */
    @GetMapping("/review")
    public ResponseEntity<?> getReviewCategory() {
        return getCategories(REVIEW_CATEGORY);
    }

    /**
     * 엔티티 상태 코드
     */
    @GetMapping("/status/{entityName}")
    public ResponseEntity<?> getEntityStatus(@PathVariable String entityName) {
        EnumClassConst eStatus = getEnumClassConst(entityName, EnumMapper.STATUS_POSTFIX);
        return getCategories(eStatus);
    }

    /**
     * 엔티티 검색 방식 타입
     */
    @GetMapping("/search-types/{entityName}")
    public ResponseEntity<?> getEntitySearchCondTypes(@PathVariable String entityName) {
        EnumClassConst eSearchCondType = getEnumClassConst(entityName, EnumMapper.SEARCH_TYPE_POSTFIX);
        return getCategories(eSearchCondType);
    }

    /**
     * 엔티티 검색 필터 타입
     */
    @GetMapping("/filter-types/{entityName}")
    public ResponseEntity<?> getEntityFilterTypes(@PathVariable String entityName) {
        EnumClassConst eSortType = getEnumClassConst(entityName, EnumMapper.FILTER_TYPE_POSTFIX);
        return getCategories(eSortType);
    }
    @GetMapping("/sort-types/{entityName}")
    public ResponseEntity<?> getEntitySortTypes(@PathVariable String entityName) {
        EnumClassConst eSortType = getEnumClassConst(entityName, EnumMapper.SORT_TYPE_POSTFIX);
        return getSortCategories(eSortType);
    }

    /**
     * EnumClassConst 에 있는 카테고리 조회
     *
     * @param category
     */
    private ResponseEntity<?> getCategories(EnumClassConst category) {
        return CustomResponse.success(category.getValue() + READ_CATEGORY, enumMapper.get(category.getClassName()));
    }
    private ResponseEntity<?> getSortCategories(EnumClassConst category) {
        return CustomResponse.success(category.getValue() + READ_CATEGORY, enumMapper.getMetaModel(category.getClassName()));
    }

    private EnumClassConst getEnumClassConst(String entityName, String path) {
        return EnumClassConst.valueOf(entityName.toUpperCase() + path);
    }
}
