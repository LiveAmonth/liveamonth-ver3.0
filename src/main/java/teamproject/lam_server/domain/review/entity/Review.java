package teamproject.lam_server.domain.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.interaction.entity.review.ReviewLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.global.entity.BaseEntity;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
@Slf4j
public class Review extends BaseEntity {

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<ReviewComment> reviewComments = new ArrayList<>();
    @OneToMany(mappedBy = "to", orphanRemoval = true)
    private final Set<ReviewLike> likes = new HashSet<>();
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<ReviewTag> tags = new HashSet<>();
    private String title;

    @Enumerated(EnumType.STRING)
    private ReviewCategory category;
    @Lob
    private String content;
    private long numberOfHits;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Basic(fetch= LAZY)
    @Formula("(select count(1) from review_like rl where rl.to_review_id = review_id)")
    private long numberOfLikes;
    @Basic(fetch= LAZY)
    @Formula("(select count(1) from review_comment rc where rc.review_id = review_id and rc.parent_comment_id is null)")
    private long numberOfComments;

    @Builder
    public Review(ReviewCategory category, String title, String content, Member member, Set<ReviewTag> tags) {
        this.category = category;
        this.title = title;
        this.content = content;
        for (ReviewTag tag : tags) {
            addTag(tag);
        }
        this.tags = tags;
        this.numberOfHits = 0L;
        setUpMember(member);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void addTag(ReviewTag tag) {
        this.tags.add(tag);
        tag.connectReview(this);
    }

    public void removeTags(List<ReviewTag> tags) {
        tags.forEach(this.tags::remove);
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
