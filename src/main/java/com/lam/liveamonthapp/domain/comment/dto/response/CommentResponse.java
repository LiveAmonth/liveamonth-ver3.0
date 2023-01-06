package com.lam.liveamonthapp.domain.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse extends BaseCommentResponse {
    private List<CommentReplyResponse> commentReplies;

    public CommentResponse(
            Long commentId,
            String comment,
            CommentProfileResponse profile,
            LocalDateTime createdDate,
            Integer numberOfLikes,
            Integer numberOfDislikes,
            List<CommentReplyResponse> commentReplies) {
        super(commentId, comment, profile, createdDate, numberOfLikes, numberOfDislikes);
        this.commentReplies = commentReplies;
    }
}
