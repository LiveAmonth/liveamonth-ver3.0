package com.lam.liveamonthapp.restdocs.utils;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.restdocs.snippet.Attributes;
import com.lam.liveamonthapp.global.enumMapper.EnumClassConst;

import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static com.lam.liveamonthapp.restdocs.utils.DocumentFormatGenerator.getDateFormat;

public interface ApiDocumentUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("https")
                        .host("api.liveamonth.com")
                        .removePort(),
                prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                removeHeaders(
                        "Vary",
                        "Transfer-Encoding",
                        "Date",
                        "Keep-Alive",
                        "Connection",
                        "X-Content-Type-Options",
                        "Pragma",
                        "Expires",
                        "X-Frame-Options",
                        "Cache-Control",
                        "X-XSS-Protection"
                ),
                prettyPrint()
        );
    }

    static RequestParametersSnippet getPageableRequestSnippet() {
        return requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("size").description("컨텐츠 수"),
                parameterWithName("sorts").description("정렬 옵션")
        );
    }

    static FieldDescriptor idFieldWithPath() {
        return fieldWithPath("id").type(NUMBER).description("id");
    }

    static FieldDescriptor idFieldWithPath(String id) {
        return fieldWithPath("id").type(NUMBER).description(id);
    }

    static FieldDescriptor titleFieldWithPath() {
        return fieldWithPath("title").type(STRING).description("제목");
    }

    static FieldDescriptor writerFieldWithPath() {
        return fieldWithPath("writer").type(STRING).description("작성자");
    }

    static FieldDescriptor contentFieldWithPath() {
        return fieldWithPath("content").type(STRING).description("내용");
    }

    static FieldDescriptor enumCodeFieldWithPath(String name, EnumClassConst type) {
        return fieldWithPath(name + ".code").type(STRING).description(DocsLinkGenerator.generateLinkCode(type));
    }

    static FieldDescriptor enumValueFieldWithPath(String name, EnumClassConst type) {
        return fieldWithPath(name + ".value").type(STRING).description(DocsLinkGenerator.generateValue(type));
    }

    static FieldDescriptor dateFieldWithPath(String field, String description) {
        return fieldWithPath(field).type(STRING).attributes(DocumentFormatGenerator.getDateFormat()).description(description);
    }

    static FieldDescriptor dateTimeFieldWithPath(String field, String description) {
        return fieldWithPath(field).type(STRING).attributes(DocumentFormatGenerator.getDateTimeFormat()).description(description);
    }

    static Attributes.Attribute getConstraintAttributes(ConstraintDescriptions constraints, String property) {
        return key("constraints").value(
                String.join("\n", constraints.descriptionsForProperty(property))
        );
    }

    static Attributes.Attribute getTitleAttributes(String title) {
        return key("title").value(title);
    }

    static CustomResponseFieldsSnippet getCheckResponseFieldsSnippet() {
        return customResponseFields("response",
                beneathPath("data").withSubsectionId("data"),
                attributes(getTitleAttributes("Check Response Fields")),
                fieldWithPath("result").type(BOOLEAN).description("결과"),
                fieldWithPath("message").type(STRING).description("결과 메시지")
        );
    }

    static CustomResponseFieldsSnippet getPostResponseFieldsSnippet() {
        return customResponseFields("response",
                beneathPath("data").withSubsectionId("data"),
                attributes(getTitleAttributes("Post ID Response Fields")),
                idFieldWithPath()
        );
    }

    static CustomResponseFieldsSnippet customResponseFields(String type,
                                                            PayloadSubsectionExtractor<?> subsectionExtractor,
                                                            Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, List.of(descriptors), attributes
                , true);
    }

    static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
