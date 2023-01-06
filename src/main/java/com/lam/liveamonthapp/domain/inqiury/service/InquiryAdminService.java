package com.lam.liveamonthapp.domain.inqiury.service;

import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerEdit;

public interface InquiryAdminService {

    void answer(Long inquiryId, InquiryAnswerCreate request);

    void edit(Long answerId, InquiryAnswerEdit editor);
}
