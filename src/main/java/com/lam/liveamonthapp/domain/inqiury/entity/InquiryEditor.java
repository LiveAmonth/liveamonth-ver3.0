package com.lam.liveamonthapp.domain.inqiury.entity;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;

@Getter
public class InquiryEditor {

    private final String title;
    private final String content;
    private final InquiryCategory category;

    @Builder
    public InquiryEditor(String title, String content, InquiryCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
