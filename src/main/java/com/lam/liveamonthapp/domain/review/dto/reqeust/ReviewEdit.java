package com.lam.liveamonthapp.domain.review.dto.reqeust;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEdit {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private String category;

    private Set<String> addedTags;
    private Set<String> removedTags;

    @Builder
    public ReviewEdit(String title, String content, String category, Set<String> addedTags, Set<String> removedTags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.addedTags = addedTags;
        this.removedTags = removedTags;
    }
}
