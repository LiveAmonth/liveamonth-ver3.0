package com.lam.liveamonthapp.domain.inqiury.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerEdit;
import com.lam.liveamonthapp.domain.inqiury.entity.Inquiry;
import com.lam.liveamonthapp.domain.inqiury.entity.InquiryAnswer;
import com.lam.liveamonthapp.domain.inqiury.entity.InquiryAnswerEditor;
import com.lam.liveamonthapp.domain.inqiury.repository.core.InquiryAnswerRepository;
import com.lam.liveamonthapp.domain.inqiury.repository.core.InquiryRepository;
import com.lam.liveamonthapp.exception.notfound.InquiryNotFound;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryAdminServiceImpl implements InquiryAdminService {

    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    @Transactional
    public void answer(Long inquiryId, InquiryAnswerCreate request) {
        InquiryAnswer answer = inquiryAnswerRepository.save(request.toEntity());

        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(InquiryNotFound::new);
        inquiry.answerInquiry(answer);
    }

    @Override
    @Transactional
    public void edit(Long answerId, InquiryAnswerEdit request) {
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.findById(answerId)
                .orElseThrow(InquiryNotFound::new);

        InquiryAnswerEditor editor = inquiryAnswer.toEditor()
                .content(request.getContent())
                .build();

        inquiryAnswer.edit(editor);
    }
}
