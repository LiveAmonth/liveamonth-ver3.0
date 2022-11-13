package teamproject.lam_server.domain.inqiury.dto.request;

import lombok.*;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswerCreate {

    @NotBlank
    private String content;

    @Builder
    public InquiryAnswerCreate(String content) {
        this.content = content;
    }

    public InquiryAnswer toEntity() {
        return InquiryAnswer.builder()
                .content(content)
                .build();
    }
}
