package teamproject.lam_server.domain.inqiury.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswerEdit {

    @NotBlank
    private String content;

    @Builder
    public InquiryAnswerEdit(String content) {
        this.content = content;
    }
}
