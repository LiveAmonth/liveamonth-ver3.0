package com.lam.liveamonthapp.domain.comment.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEdit {

    @NotEmpty
    @Length(max = 1000)
    private String comment;

    @Builder
    public CommentEdit(String comment) {
        this.comment = comment;
    }
}
