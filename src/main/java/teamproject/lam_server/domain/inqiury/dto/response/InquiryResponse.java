package teamproject.lam_server.domain.inqiury.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InquiryResponse {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private InquiryCategory category;
    private boolean isAnswered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
    private InquiryAnswerResponse answer;

    public static InquiryResponse of(Inquiry inquiry) {
        return InquiryResponse.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .writer(inquiry.getMember().getNickname())
                .content(inquiry.getContent())
                .category(inquiry.getCategory())
                .isAnswered(inquiry.isAnswered())
                .dateTime(inquiry.getLastModifiedDate())
                .answer(inquiry.isAnswered() ? InquiryAnswerResponse.of(inquiry.getAnswer()) : null)
                .build();
    }
}
