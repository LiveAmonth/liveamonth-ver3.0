package teamproject.lam_server.domain.inqiury.dto.editor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryEditor {

    private String title;
    private String content;
    private InquiryCategory category;

    @Builder
    public InquiryEditor(String title, String content, InquiryCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Inquiry toEntity(Member member){
        return Inquiry.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .member(member)
                .build();
    }
}
