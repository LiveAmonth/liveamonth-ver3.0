package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.paging.PageableDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long writeScheduleComment(Long scheduleId, @Nullable Long commentId, WriteCommentRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFound::new);
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFound::new);
        ScheduleComment comment = request.toScheduleEntity(member)
                .schedule(schedule)
                .parent(
                        commentId != null
                                ? scheduleCommentRepository.findById(commentId).orElseThrow(CommentNotFound::new)
                                : null
                )
                .build();
        return scheduleCommentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void writeReviewComment(Long reviewId, @Nullable Long commentId, WriteCommentRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFound::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFound::new);

        ReviewComment comment = request.toReviewEntity(member)
                .review(review)
                .parent(
                        commentId != null
                                ? reviewCommentRepository.findById(commentId).orElseThrow(CommentNotFound::new)
                                : null
                )
                .build();
        reviewCommentRepository.save(comment);
    }


    @Override
    public Page<CommentResponse> getScheduleComments(Long scheduleId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        scheduleCommentRepository.getTest(scheduleId, pageable);
        return null;
    }
}
