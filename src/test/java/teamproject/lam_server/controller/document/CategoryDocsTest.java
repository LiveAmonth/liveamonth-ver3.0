package teamproject.lam_server.controller.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentRequest;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentResponse;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("local")
public class CategoryDocsTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
    }

    @Test
    @DisplayName("성별 타입 조회")
    void get_gender_type() throws Exception {
        getPerform("/api/v1/categories/gender-type", "member");
    }

    @Test
    @DisplayName("계정 상태 조회")
    void get_account_state() throws Exception {
        getPerform("/api/v1/categories/account-state", "member");
    }

    @Test
    @DisplayName("1:1문의 카테고리 조회")
    void get_inquiry_category() throws Exception {
        getPerform("/api/v1/categories/inquiry", "member");
    }

    @Test
    @DisplayName("도시 이름 조회")
    void get_city_name() throws Exception {
        getPerform("/api/v1/categories/city/names", "city");
    }

    @Test
    @DisplayName("도시 소개 카테고리 조회")
    void get_city_intro_cateogry() throws Exception {
        getPerform("/api/v1/categories/city/intro", "city");
    }

    @Test
    @DisplayName("교통 수단 카테고리 조회")
    void get_transport_category() throws Exception {
        getPerform("/api/v1/categories/transport", "city");
    }

    @Test
    @DisplayName("교통 수단 등급 조회")
    void get_transport_grade() throws Exception {
        getPerform("/api/v1/categories/transport/grade", "city");
    }

    @Test
    @DisplayName("월 카테고리 조회")
    void get_month_cateogry() throws Exception {
        getPerform("/api/v1/categories/month", "city");
    }

    @Test
    @DisplayName("후기글 카테고리 조회")
    void get_review_cateogry() throws Exception {
        getPerform("/api/v1/categories/review", "review");
    }

    @Test
    @DisplayName("후기글 메뉴 그룹 조회")
    void get_review_search_group() throws Exception {
        getPerform("/api/v1/categories/review/group", "review");
    }

    @Test
    @DisplayName("도메인 검색 방식 타입 조회")
    void get_search_type() throws Exception {
        getSearchPerform("search-types", "schedule");
    }

    @Test
    @DisplayName("도메인 검색 필터 타입 조회")
    void get_filter_type() throws Exception {
        getSearchPerform("filter-types", "review");
    }

    @Test
    @DisplayName("도메인 정렬 타입 조회")
    void get_sort_type() throws Exception {
        getSearchPerform("sort-types", "schedule");
    }

    private void getSearchPerform(String urlTemplate, String entity) throws Exception {
        this.mockMvc.perform(get(urlTemplate, entity)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(entity,
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("entityName").description("도메인 이름")
                        )
                ));
    }

    private ResultActions getPerform(String type, String domain) throws Exception {
        return this.mockMvc.perform(get("/api/v1/categories/"+type+"/{entityName}")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(domain,
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
