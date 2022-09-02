package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public abstract class CommentService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    protected Member getMemberFromAuthentication(String accessToken) {
        return memberRepository.findByLoginId(
                        jwtTokenProvider.getAuthentication(accessToken).getName())
                .orElseThrow(MemberNotFound::new);
    }

    protected CommentResponse mapToCommentResponse(CommentResponse.CommentResponseBuilder builder, Long parentId, List<ScheduleComment> children) {
        return builder.commentReplies(
                children.stream()
                        .filter(comment -> comment.getParent().getId().equals(parentId))
                        .map(CommentReplyResponse::of).collect(Collectors.toList())
        ).build();
    }

    abstract CommentType getType();

    public abstract Page<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO);

    public abstract void writeComment(String accessToken, Long contentId, Long commentId, WriteCommentRequest request);

    public abstract CommentResponse getBestComments(Long contentId);
}
