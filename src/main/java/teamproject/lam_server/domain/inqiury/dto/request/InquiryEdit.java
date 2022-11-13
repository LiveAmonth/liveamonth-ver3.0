package teamproject.lam_server.domain.inqiury.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryEdit {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private String category;

    @Builder
    public InquiryEdit(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
