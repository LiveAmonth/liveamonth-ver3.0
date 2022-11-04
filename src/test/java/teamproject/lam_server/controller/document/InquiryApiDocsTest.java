package teamproject.lam_server.controller.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.repository.InquiryRepository;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.INQUIRY_CATEGORY;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentRequest;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentResponse;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateValue;
import static teamproject.lam_server.utils.DocumentFormatGenerator.getDateTimeFormat;

public class InquiryApiDocsTest extends ApiDocsTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SecurityContextFinder finder;

    @Autowired
    InquiryRepository inquiryRepository;

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @WithMockCustomUser(loginId = "inquiry")
    @DisplayName("1:1 문의글 작성")
    void write_inquiry() throws Exception {
        InquiryCreate inquiryCreate = InquiryCreate.builder()
                .title("1:1 문의글 테스트 제목")
                .category(InquiryCategory.ETC.name())
                .content("1:1 문의글 테스트 내용")
                .build();

        ResultActions result = this.mockMvc.perform(post("/api/v1/inquiries")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inquiryCreate)))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-post",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("category").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY))
                )
        ));
    }

    @Test
    @WithMockCustomUser(loginId = "inquiry")
    @DisplayName("1:1 문의 다건 조회")
    void get_inquiry_list() throws Exception {
        Inquiry inquiry1 = InquiryCreate.builder()
                .title("1:1 문의글 테스트 제목1")
                .category(InquiryCategory.SCHEDULE.name())
                .content("1:1 문의글 테스트 내용1")
                .build()
                .toEntity(finder.getLoggedInMember());
        Inquiry inquiry2 = InquiryCreate.builder()
                .title("1:1 문의글 테스트 제목2")
                .category(InquiryCategory.ETC.name())
                .content("1:1 문의글 테스트 내용2")
                .build()
                .toEntity(finder.getLoggedInMember());
        inquiryRepository.saveAll(List.of(inquiry1, inquiry2));

        ResultActions result = this.mockMvc.perform(get("/api/v1/inquiries/list")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sorts", "id,desc")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("컨텐츠 수"),
                        parameterWithName("sorts").description("정렬 옵션")
                ),
                responseFields(
                        beneathPath("data.content[]").withSubsectionId("content"),
                        fieldWithPath("id").type(NUMBER).description("아이디"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("writer").type(STRING).description("작성자"),
                        fieldWithPath("category.code").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY)),
                        fieldWithPath("category.value").type(STRING).description(generateValue(INQUIRY_CATEGORY)),
                        fieldWithPath("answered").type(BOOLEAN).description("답변 여부"),
                        fieldWithPath("date").type(STRING).attributes(getDateTimeFormat()).description("작성 시간")
                )
        ));
    }

}
