package teamproject.lam_server.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public interface ApiDocumentUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
//        return preprocessRequest(
//                modifyUris()
//                        .scheme("https")
//                        .host("docs.api.com")
//                        .removePort(),
//                prettyPrint()
//        );
        return preprocessRequest(
                prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }


    static FieldDescriptor[] getEnumTypeDescriptor(String name, String property) {
        return new FieldDescriptor[]{fieldWithPath(property + ".code").type(JsonFieldType.OBJECT).description(name + "코드"),
                fieldWithPath(property + ".value").type(JsonFieldType.STRING).description(name + "이름")};
    }
}
