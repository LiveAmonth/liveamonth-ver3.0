package com.lam.liveamonthapp.domain.comment.entity;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.global.entity.BaseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Getter
@MappedSuperclass
public abstract class CommentEntity extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    protected Member member;

    @Length(max = 1000)
    protected String comment;


    public CommentEditor.CommentEditorBuilder toEditor() {
        return CommentEditor.builder()
                .comment(comment);
    }

    public void edit(CommentEditor editor) {
        comment = editor.getComment();
    }

    protected abstract void setUpWriter(Member member);

    public abstract CommentEntity getParent();

    public abstract int getNumberOfLikes();

    public abstract int getNumberOfDislikes();

    public abstract List<? extends CommentEntity> getChildren();
}
