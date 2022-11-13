package teamproject.lam_server.domain.inqiury.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InquiryAnswerEditor {

    private final String content;

    @Builder
    public InquiryAnswerEditor(String content) {
        this.content = content;
    }
}
