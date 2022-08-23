package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    @Transactional
    public Long writeScheduleComment(String accessToken, Long scheduleId, Long commentId, WriteCommentRequest request) {
        // access token 으로 작성자 검색
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Member member = memberRepository.findByLoginId(authentication.getName()).orElseThrow(MemberNotFound::new);

        // 댓글을 자성한 게시물 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFound::new);

        ScheduleComment comment = request.toScheduleEntity(member)
                .schedule(schedule)
                .parent(scheduleCommentRepository.findById(commentId).orElse(null))
                .build();

        return scheduleCommentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void writeReviewComment(String accessToken, Long reviewId, Long commentId, WriteCommentRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Member member = memberRepository.findByLoginId(authentication.getName()).orElseThrow(MemberNotFound::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFound::new);

        ReviewComment comment = request.toReviewEntity(member)
                .review(review)
                .parent(
                        commentId != 0
                                ? reviewCommentRepository.findById(commentId).orElseThrow(CommentNotFound::new)
                                : null
                )
                .build();
        reviewCommentRepository.save(comment);
    }


    @Override
    public Page<CommentResponse> getScheduleComments(Long scheduleId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<ScheduleComment> scheduleComments = scheduleCommentRepository.getScheduleComments(scheduleId, pageable);
        List<ScheduleComment> scheduleCommentReplies = scheduleComments.getNumberOfElements() != 0
                ? getScheduleCommentReplies(scheduleId, scheduleComments)
                : Collections.emptyList();
        return scheduleComments.map(comment -> mapToCommentResponse(
                CommentResponse.of(comment),
                comment.getId(),
                scheduleCommentReplies));
    }

    private CommentResponse mapToCommentResponse(CommentResponse.CommentResponseBuilder builder, Long parentId, List<ScheduleComment> children) {
        return builder.commentReplies(
                children.stream()
                        .filter(comment -> comment.getParent().getId().equals(parentId))
                        .map(CommentReplyResponse::of).collect(Collectors.toList())
        ).build();
    }

    private List<ScheduleComment> getScheduleCommentReplies(Long scheduleId, Page<ScheduleComment> comments) {
        Long from = comments.getContent().get(comments.getNumberOfElements() - 1).getId();
        Long to = comments.getContent().get(0).getId();
        return scheduleCommentRepository.getScheduleCommentReplies(scheduleId, from, to);
    }
}
