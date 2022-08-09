package teamproject.lam_server.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import teamproject.lam_server.domain.interaction.entity.ReviewLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.editor.ReviewEditor;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @JsonIgnore
    @OneToMany(mappedBy = "review")
    private final List<ReviewReply> reviewReplies = new ArrayList<>();

    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    private final Set<ReviewLike> likes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReviewCategory reviewCategory;
    private String title;
    @Lob
    private String content;
    private LocalDateTime reviewDateTime;
    private long viewCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Review(ReviewCategory reviewCategory, String title, String content, LocalDateTime reviewDateTime, Member member) {
        this.reviewCategory = reviewCategory;
        this.title = title;
        this.content = content;
        this.reviewDateTime = reviewDateTime;
        this.viewCount = 0;
        setMember(member);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }
//
//    public ReviewEditor.ReviewEditorBuilder toEditor(){
//        return ReviewEditor.builder()
//                .title(title)
//                .content(content)
//                .reviewCategory(reviewCategory);
//    }

    public void edit(ReviewEditor reviewEditor) {
        this.title = reviewEditor.getTitle();
        this.reviewCategory = reviewEditor.getReviewCategory();
        this.content = reviewEditor.getContent();
    }
}
