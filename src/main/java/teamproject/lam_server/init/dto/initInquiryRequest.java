package teamproject.lam_server.init.dto;

import lombok.Data;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.member.entity.Member;

@Data
public class initInquiryRequest {
    private final String title;
    private final String content;
    private final InquiryCategory category;

    public Inquiry toEntity(Member member){
        return Inquiry.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .member(member)
                .build();
    }
}
