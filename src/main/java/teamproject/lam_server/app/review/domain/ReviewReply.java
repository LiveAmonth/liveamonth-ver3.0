package teamproject.lam_server.app.review.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.app.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "review_replies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_reply_id")
    private Long id;

    @Lob
    private String content;
    @Column(name = "review_reply_date")
    private LocalDateTime reviewReplyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ref_review_reply_id")
    private ReviewReply parent;

    // 자식 정의
    @OneToMany(mappedBy = "parent")
    private List<ReviewReply> child;
}
