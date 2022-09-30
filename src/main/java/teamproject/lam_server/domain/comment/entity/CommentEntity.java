package teamproject.lam_server.domain.comment.entity;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseEntity;

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
    protected String content;

    protected abstract void setUpWriter(Member member);

    public abstract CommentEntity getParent();

    public abstract int getLikeCount();
    public abstract int getDislikeCount();

    public abstract List<? extends CommentEntity> getChildren();

}
