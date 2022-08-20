package teamproject.lam_server.domain.comment.entity;

import lombok.Getter;
import lombok.ToString;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
@Getter
public abstract class CommentEntity extends BaseTimeEntity {

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    protected Member member;
}
