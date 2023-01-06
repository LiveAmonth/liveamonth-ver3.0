package com.lam.liveamonthapp.domain.review.dto.response;

import lombok.Getter;
import com.lam.liveamonthapp.util.DateTimeUtil;
import com.lam.liveamonthapp.util.StringUtil;

import java.time.LocalDateTime;

@Getter
public class ReviewListResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final String elapsedTime;
    private final long numberOfHits;
    private final long numberOfComments;
    private final long numberOfLikes;

    public ReviewListResponse(Long id, String writer, String title, String content, LocalDateTime createdDate, long numberOfHits, long numberOfComments, long numberOfLikes) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = StringUtil.removeHtmlTag(content);
        this.elapsedTime = DateTimeUtil.calcTimeBefore(createdDate);
        this.numberOfHits = numberOfHits;
        this.numberOfComments = numberOfComments;
        this.numberOfLikes = numberOfLikes;
    }
}
