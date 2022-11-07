package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import teamproject.lam_server.controller.ApiDocsTest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentRequest;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentResponse;

public class CategoryDocsTest extends ApiDocsTest {

    @Test
    @DisplayName("성별 타입 조회")
    void get_gender_type() throws Exception {
        getPerform("/gender-type", "gender-type");
    }

    @Test
    @DisplayName("계정 상태 조회")
    void get_account_state() throws Exception {
        getPerform("/account-state", "account-state");
    }

    @Test
    @DisplayName("1:1문의 카테고리 조회")
    void get_inquiry_category() throws Exception {
        getPerform("/inquiry", "inquiry");
    }

    @Test
    @DisplayName("도시 이름 조회")
    void get_city_name() throws Exception {
        getPerform("/city/names", "city-name");
    }

    @Test
    @DisplayName("도시 소개 카테고리 조회")
    void get_city_intro_category() throws Exception {
        getPerform("/city/intro", "city-intro-category");
    }

    @Test
    @DisplayName("교통 수단 카테고리 조회")
    void get_transport_category() throws Exception {
        getPerform("/transport", "transport-category");
    }

    @Test
    @DisplayName("교통 수단 등급 조회")
    void get_transport_grade() throws Exception {
        getPerform("/transport/grade", "transport-grade");
    }

    @Test
    @DisplayName("월 카테고리 조회")
    void get_month_category() throws Exception {
        getPerform("/month", "city-month");
    }

    @Test
    @DisplayName("후기글 카테고리 조회")
    void get_review_category() throws Exception {
        getPerform("/review", "review-cateogry");
    }

    @Test
    @DisplayName("후기글 메뉴 그룹 조회")
    void get_review_search_group() throws Exception {
        getPerform("/review/group", "review-search-group");
    }

    @Test
    @DisplayName("도메인 검색 방식 타입 조회")
    void get_search_type() throws Exception {
        getSearchPerform("search-types", "review");
    }

    @Test
    @DisplayName("도메인 검색 필터 타입 조회")
    void get_filter_type() throws Exception {
        getSearchPerform("filter-types", "schedule");
    }

    @Test
    @DisplayName("도메인 정렬 타입 조회")
    void get_sort_type() throws Exception {
        getSearchPerform("sort-types", "schedule");
    }

    @Test
    @DisplayName("상호작용 타입 조회")
    void get_interaction_type() throws Exception {
        getPerform("/interaction/type", "interaction-type");
    }

    @Test
    @DisplayName("상호작용 상태 조회")
    void get_interaction_state() throws Exception {
        getPerform("/interaction/state", "interaction-state");
    }

    @Test
    @DisplayName("댓글 게시물 타입 조회")
    void get_comment_type() throws Exception {
        getPerform("/comment/type", "comment-type");
    }


    private void getSearchPerform(String type, String entity) throws Exception {
        this.mockMvc.perform(get("/api/v1/categories/" + type + "/{entityName}", entity)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(entity + "-" + type,
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("entityName").description("도메인 이름")
                        )
                ));
    }

    private void getPerform(String urlTemplate, String domain) throws Exception {
        this.mockMvc.perform(get("/api/v1/categories" + urlTemplate)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(domain,
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
