package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewTag;
import teamproject.lam_server.domain.review.entity.Tag;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.domain.review.repository.core.ReviewTagRepository;
import teamproject.lam_server.domain.review.repository.core.TagRepository;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.PageableDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.REVIEW_CATEGORY;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.REVIEW_SEARCH_TYPE;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;

public class ReviewApiDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/api/v1/reviews";
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewTagRepository reviewTagRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("후기글 작성")
    @WithMockCustomUser
    void review_write() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        ReviewCreate request = ReviewCreate.builder()
                .title("review title")
                .content("review content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Set.of("seoul", "liveamonth"))
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(ReviewCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/{login_id}", authMember.getLoginId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("review-write",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("로그인 아이디")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(constraints, "title")),
                        contentFieldWithPath()
                                .attributes(getConstraintAttributes(constraints, "content")),
                        fieldWithPath("category").type(STRING)
                                .description(generateLinkCode(REVIEW_CATEGORY))
                                .attributes(getConstraintAttributes(constraints, "category")),
                        fieldWithPath("tags").type(ARRAY).description("태그").optional()
                ),
                getPostResponseFieldsSnippet()
        ));
    }

    @Test
    @Transactional
    @DisplayName("후기글 수정")
    @WithMockCustomUser
    void edit_review() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Review review = createReview(authMember);

        ReviewEdit request = ReviewEdit.builder()
                .title("edited title")
                .content("edited content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .addedTags(Set.of("서울"))
                .removedTags(Set.of("seoul"))
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(ReviewCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{review_id}", review.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("review-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("review_id").description("후기글 id")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(constraints, "title")),
                        contentFieldWithPath()
                                .attributes(getConstraintAttributes(constraints, "content")),
                        fieldWithPath("category").type(STRING)
                                .description(generateLinkCode(REVIEW_CATEGORY))
                                .attributes(getConstraintAttributes(constraints, "category")),
                        fieldWithPath("addedTags").type(ARRAY).description("추가된 태그"),
                        fieldWithPath("removedTags").type(ARRAY).description("삭제된 태그")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("후기글 삭제")
    @WithMockCustomUser
    void delete_review() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Review review = createReview(authMember);

        // when
        ResultActions result = this.mockMvc.perform(
                delete(BASIC_URL + "/{review_id}", review.getId())
                        .accept(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("review-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("review_id").description("후기글 id")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("후기글 단건 조회")
    void get_review() throws Exception {
        // given
        Member member = createMember();
        Review review = createReview(member);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/{review_id}/detail", review.getId())
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(review.getId()))
                .andExpect(jsonPath("$.data.title").value(review.getTitle()))
                .andExpect(jsonPath("$.data.profile.nickname").value(member.getNickname()));

        // then
        result.andDo(document("review-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("review_id").description("후기글 id")
                ),
                customResponseFields("format-response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Review Detail Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        contentFieldWithPath(),
                        subsectionWithPath("profile").type(OBJECT).description("작성자 프로필"),
                        enumCodeFieldWithPath("category", REVIEW_CATEGORY),
                        enumValueFieldWithPath("category", REVIEW_CATEGORY),
                        fieldWithPath("tags").type(ARRAY).description("태그 리스트"),
                        dateTimeFieldWithPath("createDateTime", "작성 시간"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수")
                ),
                customResponseFields("response",
                        beneathPath("data.profile").withSubsectionId("profile"),
                        attributes(getTitleAttributes("Member Profile Response Fields")),
                        idFieldWithPath(),
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("프로필 이미지(URL)"),
                        fieldWithPath("numberOfReviews").type(NUMBER).description("후기글 갯수"),
                        fieldWithPath("numberOfSchedules").type(NUMBER).description("스케줄 갯수"),
                        fieldWithPath("numberOfFollowers").type(NUMBER).description("팔로워 갯수"),
                        fieldWithPath("numberOfFollows").type(NUMBER).description("팔로우 갯수")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("후기글 검색")
    void search_review() throws Exception {
        // given
        Member member = createMember();
        createReviews(member);
        ReviewSearchCond cond = ReviewSearchCond.builder()
                .type(ReviewSearchType.REVIEW_LIVEAMONTH)
                .category(ReviewCategory.SE_REVIEW)
                .searchWord("review")
                .build();

        MultiValueMap<String, String> tags = new LinkedMultiValueMap<>();
        tags.put("tags", Collections.emptyList());

        PageableDTO pageable = PageableDTO.builder().build();

        // when
        ResultActions result = this.mockMvc.perform(
                get(BASIC_URL + "/search")
                        .accept(APPLICATION_JSON)
                        .param("searchWord", cond.getSearchWord())
                        .params(tags)
                        .param("type", cond.getType().getCode())
                        .param("category", cond.getCategory().getCode())
                        .param("page", pageable.getPage().toString())
                        .param("size", pageable.getSize().toString())
                        .param("sorts", "id,desc")
        ).andExpect(status().isOk());

        // then
        result.andDo(document("review-search",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                        parameterWithName("searchWord").description("검색 단어").optional(),
                        parameterWithName("tags").description("태그").optional(),
                        parameterWithName("type").description(generateLinkCode(REVIEW_SEARCH_TYPE)).optional(),
                        parameterWithName("category").description(generateLinkCode(REVIEW_CATEGORY)).optional(),
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("컨텐츠 수"),
                        parameterWithName("sorts").description("정렬 옵션")
                ),
                customResponseFields("response",
                        beneathPath("data.content[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Review List Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        writerFieldWithPath(),
                        contentFieldWithPath(),
                        fieldWithPath("elapsedTime").type(STRING).description("작성 시간(전)"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("사용자 후기글 조회")
    @WithMockCustomUser
    void get_review_by_member() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        List<Review> reviews = createReviews(authMember);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/list/{login_id}", authMember.getLoginId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                                .param("size", String.valueOf(3))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", reviews.get(1).getId()).exists())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", reviews.get(2).getId()).exists());

        // then
        result.andDo(document("my-review-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                requestParameters(
                        parameterWithName("size").description("사이즈").optional(),
                        parameterWithName("last_id").description("마지막 id").optional()
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Review List Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        writerFieldWithPath(),
                        contentFieldWithPath(),
                        fieldWithPath("elapsedTime").type(STRING).description("작성 시간(전)"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("후기글 조회수 증가")
    void review_view_count_up() throws Exception {
        // given
        Member member = createMember();
        Review review = createReview(member);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{review_id}/count-up", review.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then
        result.andDo(document("review-view-count-up",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("review_id").description("후기글 id")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("추천 태그 다건 조회")
    void get_recommendation_tag_list() throws Exception {
        // given
        Member member = createMember();
        List<Review> reviews = createReviews(member);

        // when
        ResultActions result = this.mockMvc.perform(
                get(BASIC_URL + "/recommendation-tags")
                        .accept(APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then
        result.andDo(document("recommendation-tags-get",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Tag response Fields")),
                        fieldWithPath("name").type(STRING).description("태그 이름")
                )
        ));
    }

    Member createMember() {
        MemberCreate memberCreate =
                MemberCreate.builder()
                        .loginId("member")
                        .password("memberPassword1!")
                        .name("memberName")
                        .nickname("memberNickname")
                        .email("member@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        return memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    Review createReview(Member member) {
        ReviewCreate create = ReviewCreate.builder()
                .title("review title")
                .content("review content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Set.of("seoul", "liveamonth"))
                .build();
        return reviewRepository.save(create.toEntity(member, mapToReviewTags(create.getTags())));
    }

    List<Review> createReviews(Member member) {
        List<ReviewCreate> creates = List.of(
                ReviewCreate.builder()
                        .title("review title one")
                        .content("review content one")
                        .category(ReviewCategory.SE_REVIEW.getCode())
                        .tags(Set.of("seoul", "liveamonth"))
                        .build(),
                ReviewCreate.builder()
                        .title("review title two")
                        .content("review content two")
                        .category(ReviewCategory.QUESTION.getCode())
                        .tags(Set.of("question", "liveamonth"))
                        .build(),
                ReviewCreate.builder()
                        .title("title three")
                        .content("content three")
                        .category(ReviewCategory.SE_REVIEW.getCode())
                        .tags(Set.of("seoul", "liveamonth"))
                        .build()
        );
        return reviewRepository.saveAll(
                creates.stream()
                        .map(create -> create.toEntity(member, mapToReviewTags(create.getTags())))
                        .collect(Collectors.toList())
        );
    }

    Tag findOrCreateTag(String tag) {
        return tagRepository.findByName(tag)
                .orElseGet(
                        () -> tagRepository.save(
                                Tag.builder().name(tag).build()
                        )
                );
    }

    Set<ReviewTag> mapToReviewTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> ReviewTag.createReviewTag(findOrCreateTag(tag)))
                .collect(Collectors.toSet());
    }
}
