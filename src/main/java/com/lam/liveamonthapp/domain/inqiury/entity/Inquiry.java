package com.lam.liveamonthapp.domain.inqiury.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.global.entity.BaseEntity;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "inquiry_id"))
public class Inquiry extends BaseEntity {

    private String title;
    @Lob
    private String content;

    @Enumerated(STRING)
    private InquiryCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inquiry_answer_id")
    private InquiryAnswer answer;

    private boolean isAnswered;

    @Builder
    public Inquiry(String title, String content, InquiryCategory category, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.isAnswered = false;
    }

    public void answerInquiry(InquiryAnswer inquiryAnswer) {
        this.isAnswered = true;
        this.answer = inquiryAnswer;
        inquiryAnswer.answered(this);
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
}
