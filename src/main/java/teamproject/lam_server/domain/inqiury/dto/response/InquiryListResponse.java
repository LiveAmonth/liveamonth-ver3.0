package teamproject.lam_server.domain.inqiury.dto.response;

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
    private LocalDateTime lastModified;

    public static InquiryListResponse of(Inquiry inquiry) {
        return InquiryListResponse.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .writer(inquiry.getMember().getNickname())
                .category(inquiry.getCategory())
                .isAnswered(inquiry.isAnswered())
                .lastModified(inquiry.getLastModifiedDate())
                .build();
    }
}
