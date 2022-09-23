package teamproject.lam_server.domain.inqiury.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InquiryResponse {

    private String title;
    private String writer;
    private String content;
    private InquiryCategory category;
    private boolean isAnswered;
    private LocalDateTime lastModified;
    private InquiryAnswerResponse answer;

    public static InquiryResponse of(Inquiry inquiry) {
        return InquiryResponse.builder()
                .title(inquiry.getTitle())
                .writer(inquiry.getMember().getNickname())
                .content(inquiry.getContent())
                .category(inquiry.getCategory())
                .isAnswered(inquiry.isAnswered())
                .lastModified(inquiry.getLastModifiedDate())
                .answer(InquiryAnswerResponse.of(inquiry.getAnswer()))
                .build();
    }
}
