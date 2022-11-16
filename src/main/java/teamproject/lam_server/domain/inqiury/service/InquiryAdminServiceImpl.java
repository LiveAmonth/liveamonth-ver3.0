package teamproject.lam_server.domain.inqiury.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerEdit;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswerEditor;
import teamproject.lam_server.domain.inqiury.repository.core.InquiryAnswerRepository;
import teamproject.lam_server.domain.inqiury.repository.core.InquiryRepository;
import teamproject.lam_server.exception.notfound.InquiryNotFound;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryAdminServiceImpl implements InquiryAdminService {

    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final InquiryRepository inquiryRepository;

    public void answer(Long inquiryId, InquiryAnswerCreate request) {
        InquiryAnswer answer = inquiryAnswerRepository.save(request.toEntity());

        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(InquiryNotFound::new);
        inquiry.answerInquiry(answer);
    }

    public void edit(Long answerId, InquiryAnswerEdit request) {
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.findById(answerId)
                .orElseThrow(InquiryNotFound::new);

        InquiryAnswerEditor editor = inquiryAnswer.toEditor()
                .content(request.getContent())
                .build();

        inquiryAnswer.edit(editor);
    }
}
