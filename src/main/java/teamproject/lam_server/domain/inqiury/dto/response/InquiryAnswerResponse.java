package teamproject.lam_server.domain.inqiury.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;

import java.time.LocalDateTime;

@Getter
@Builder
public class InquiryAnswerResponse {

    private String title;
    private String writer;
    private String content;
    private LocalDateTime lastModified;

    public static InquiryAnswerResponse of(InquiryAnswer answer) {
        return InquiryAnswerResponse.builder()
                .title(answer.getTitle())
                .writer(answer.getWriter())
                .content(answer.getContent())
                .lastModified(answer.getLastModifiedDate())
                .build();
    }
}
