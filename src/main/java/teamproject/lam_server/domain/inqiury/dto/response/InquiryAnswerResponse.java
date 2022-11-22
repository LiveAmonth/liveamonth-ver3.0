package teamproject.lam_server.domain.inqiury.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;

import java.time.LocalDateTime;

@Getter
@Builder
public class InquiryAnswerResponse {

    private Long id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    public static InquiryAnswerResponse of(InquiryAnswer answer) {
        return InquiryAnswerResponse.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .dateTime(answer.getLastModifiedDate())
                .build();
    }
}
