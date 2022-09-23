package teamproject.lam_server.domain.inqiury.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "inquiry_answer_id"))
public class InquiryAnswer extends BaseTimeEntity {
    private String title;
    private String content;
    private String writer;

    @Builder
    public InquiryAnswer(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
