package com.lam.liveamonthapp.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentEdit;
import com.lam.liveamonthapp.domain.comment.dto.response.BestCommentResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentResponse;
import com.lam.liveamonthapp.domain.comment.entity.ScheduleComment;
import com.lam.liveamonthapp.domain.comment.repository.ScheduleCommentQueryRepository;
import com.lam.liveamonthapp.domain.comment.repository.ScheduleCommentRepository;
import com.lam.liveamonthapp.exception.notfound.CommentNotFound;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleCommentService extends CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ScheduleCommentQueryRepository scheduleCommentQueryRepository;

    public ScheduleCommentService(SecurityContextFinder finder,
                                  ScheduleCommentRepository scheduleCommentRepository,
                                  ScheduleCommentQueryRepository scheduleCommentQueryRepository) {
        super(finder);
        this.scheduleCommentRepository = scheduleCommentRepository;
        this.scheduleCommentQueryRepository = scheduleCommentQueryRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void writeComment(Long contentId, CommentCreate request) {
        scheduleCommentRepository.write(
                finder.getAuthenticationName(), finder.getLoggedInMemberId(), contentId, request
        );
    }

    @Override
    @Transactional
    public void editComment(Long commentId, CommentEdit request) {
        super.edit(
                scheduleCommentRepository
                        .findById(commentId)
                        .orElseThrow(CommentNotFound::new),
                request
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        ScheduleComment comment = scheduleCommentRepository
                .findById(commentId)
                .orElseThrow(CommentNotFound::new);
        finder.checkLegalWriterOfPost(comment);
        scheduleCommentRepository.delete(comment);
    }

    @Override
    public CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<CommentResponse> reviewComments = scheduleCommentQueryRepository.getComments(contentId, pageable);

        return CustomPage.<CommentResponse>builder()
                .page(reviewComments)
                .build();
    }

    @Override
    public List<BestCommentResponse> getBestComments(Long contentId) {
        return scheduleCommentQueryRepository.getBestComments(contentId);
    }
}
