package teamproject.lam_server.domain.inqiury.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.global.entity.BaseEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "inquiry_answer_id"))
public class InquiryAnswer extends BaseEntity {
    private String content;

    @OneToOne(mappedBy = "answer")
    private Inquiry inquiry;

    @Builder
    public InquiryAnswer(String content) {
        this.content = content;
    }

    public InquiryAnswerEditor.InquiryAnswerEditorBuilder toEditor() {
        return InquiryAnswerEditor.builder()
                .content(content);
    }

    public void edit(InquiryAnswerEditor editor) {
        this.content = editor.getContent();
    }

    public void answered(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
