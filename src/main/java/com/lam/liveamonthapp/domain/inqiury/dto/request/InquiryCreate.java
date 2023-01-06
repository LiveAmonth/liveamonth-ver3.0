package com.lam.liveamonthapp.domain.inqiury.dto.request;

import lombok.*;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;
import com.lam.liveamonthapp.domain.inqiury.entity.Inquiry;
import com.lam.liveamonthapp.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryCreate {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private String category;

    @Builder
    public InquiryCreate(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Inquiry toEntity(Member member){
        return Inquiry.builder()
                .title(this.title)
                .content(this.content)
                .category(InquiryCategory.valueOf(this.category))
                .member(member)
                .build();
    }
}
