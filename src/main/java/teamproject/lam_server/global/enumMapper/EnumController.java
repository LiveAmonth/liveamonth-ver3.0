package teamproject.lam_server.global.enumMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.global.dto.CustomResponse;

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
        return CustomResponse.getCategorySuccess(enumMapper, GENDER_TYPE);
    }

    /**
     * 계정 상태
     */
    @GetMapping("/account-state")
    public ResponseEntity<?> getAccountState() {
        return CustomResponse.getCategorySuccess(enumMapper, ACCOUNT_STATE);
    }

    /**
     * 도시 이름
     */
    @GetMapping("/city/name")
    public ResponseEntity<?> getCityName() {
        return CustomResponse.getCategorySuccess(enumMapper, CITY_NAME);
    }

    /**
     * 도시 소개 카테고리
     */
    @GetMapping("/city/info")
    public ResponseEntity<?> getCityInfo() {
        return CustomResponse.getCategorySuccess(enumMapper, CITY_INFO_CATEGORY);
    }

    /**
     * 교통 수단 카테고리
     */
    @GetMapping("/transport")
    public ResponseEntity<?> getTransport() {
        return CustomResponse.getCategorySuccess(enumMapper, TRANSPORT_CATEGORY);
    }

    /**
     * 교통 등급
     */
    @GetMapping("/transport/grade")
    public ResponseEntity<?> getTransportGrade() {
        return CustomResponse.getCategorySuccess(enumMapper, TRANSPORT_GRADE);
    }

    /**
     * 월 카테고리
     */
    @GetMapping("/month")
    public ResponseEntity<?> getMonth() {
        return CustomResponse.getCategorySuccess(enumMapper, MONTH_CATEGORY);
    }

    /**
     * 엔티티 상태 코드
     */
    @GetMapping("/status/{entityName}")
    public ResponseEntity<?> getEntityStatus(@PathVariable String entityName) {
        EnumClassConst eStatus = EnumUtil.getEnumClassConst(entityName, EnumMapper.STATUS_PATH);
        return CustomResponse.getCategorySuccess(enumMapper, eStatus);
    }

    /**
     * 엔티티 검색 방식 타입
     */
    @GetMapping("/search-conditions/{entityName}")
    public ResponseEntity<?> getEntitySearchCondTypes(@PathVariable String entityName) {
        EnumClassConst eSearchCondType = EnumUtil.getEnumClassConst(entityName, EnumMapper.SEARCH_TYPE_PATH);
        return CustomResponse.getCategorySuccess(enumMapper, eSearchCondType);
    }

    /**
     * 엔티티 검색 정렬 타입
     */
    @GetMapping("/sort-types/{entityName}")
    public ResponseEntity<?> getEntitySortTypes(@PathVariable String entityName) {
        EnumClassConst eSortType = EnumUtil.getEnumClassConst(entityName, EnumMapper.SORT_TYPE_PATH);
        return CustomResponse.getCategorySuccess(enumMapper, eSortType);
    }


}
