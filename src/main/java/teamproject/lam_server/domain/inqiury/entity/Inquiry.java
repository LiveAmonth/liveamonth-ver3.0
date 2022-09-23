package teamproject.lam_server.domain.inqiury.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "inquiry_id"))
public class Inquiry extends BaseTimeEntity {

    private String title;
    private String content;

    @Enumerated(STRING)
    private InquiryCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inquiry_answer_id")
    private InquiryAnswer answer;

    private boolean isAnswered;
    private boolean isRemoved;
    @Builder
    public Inquiry(String title, String content, InquiryCategory category, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.isAnswered = false;
        this.isRemoved = false;
    }

    public void answerInquiry(InquiryAnswer answer) {
        this.isAnswered = true;
        this.answer = answer;
    }

    public InquiryEditor.InquiryEditorBuilder toEditor() {
        return InquiryEditor.builder()
                .title(title)
                .content(content)
                .category(category);
    }

    public void edit(InquiryEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
        this.category = editor.getCategory();
    }

    public void remove(){
        this.isRemoved = true;
    }
}
