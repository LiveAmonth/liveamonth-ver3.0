package teamproject.lam_server.domain.inqiury.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;

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
    private InquiryCategory category;
}
