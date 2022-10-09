package teamproject.lam_server.domain.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.interaction.entity.review.ReviewLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.global.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
public class Review extends BaseEntity {

    @OneToMany(mappedBy = "review")
    private final List<ReviewComment> reviewComments = new ArrayList<>();
    @OneToMany(mappedBy = "to")
    private final Set<ReviewLike> likes = new HashSet<>();
    private String title;

    @Enumerated(EnumType.STRING)
    private ReviewCategory category;
    private String content;
    private long numberOfHits;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ElementCollection
    @CollectionTable(name = "review_tag", joinColumns =
    @JoinColumn(name = "review_id")
    )
    @Column(name = "tag_name")
    private Set<String> tags = new HashSet<>();

    @Formula("(select count(1) from review_like rl where rl.to_review_id = review_id)")
    private long numberOfLikes;
    @Formula("(select count(1) from review_comment rc where rc.review_id = review_id)")
    private long numberOfComments;

    @Builder
    public Review(ReviewCategory category, String title, String content, Member member, Set<String> tags) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.numberOfHits = 0L;
        setUpMember(member);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public ReviewEditor.ReviewEditorBuilder toEditor() {
        return ReviewEditor.builder()
                .title(title)
                .content(content)
                .category(category);
    }

    public void edit(ReviewEditor reviewEditor) {
        this.title = reviewEditor.getTitle();
        this.category = reviewEditor.getCategory();
        this.content = reviewEditor.getContent();
    }
}
