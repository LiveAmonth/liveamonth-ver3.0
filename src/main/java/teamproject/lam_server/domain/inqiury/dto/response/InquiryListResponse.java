package teamproject.lam_server.domain.inqiury.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;

import java.time.LocalDateTime;

@Getter
@Builder
public class InquiryListResponse {

    private Long id;
    private String title;
    private String writer;
    private InquiryCategory category;
    private boolean isAnswered;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime lastModifiedDate;

    public static InquiryListResponse of(Inquiry inquiry) {
        return InquiryListResponse.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .writer(inquiry.getMember().getNickname())
                .category(inquiry.getCategory())
                .isAnswered(inquiry.isAnswered())
                .lastModifiedDate(inquiry.getLastModifiedDate())
                .build();
    }
}
