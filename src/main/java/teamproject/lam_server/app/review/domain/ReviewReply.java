package teamproject.lam_server.app.review.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.app.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "review_replies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReply {

    @Id
    @GeneratedValue
    @Column(name = "review_reply_id")
    private Long id;

    @Lob
    private String content;
    @Column(name = "review_reply_date")
    private LocalDateTime reviewReplyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_review_reply_id")
    private ReviewReply superReviewReply;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "superReviewReply", cascade = CascadeType.ALL)
    private List<ReviewReply> subReviewReplies;
}
