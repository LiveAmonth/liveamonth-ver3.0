package teamproject.lam_server.domain.inqiury.service;

import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerEdit;

public interface InquiryAdminService {

    void answer(Long inquiryId, InquiryAnswerCreate request);

    void edit(Long answerId, InquiryAnswerEdit editor);
}
