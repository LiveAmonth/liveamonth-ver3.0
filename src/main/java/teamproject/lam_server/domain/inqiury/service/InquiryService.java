package teamproject.lam_server.domain.inqiury.service;

import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

public interface InquiryService {

    PostIdResponse write(InquiryCreate request);
    CustomPage<InquiryListResponse> getInquires(PageableDTO pageableDTO);

    void edit(Long id, InquiryEdit editor);

    void delete(Long id);

    InquiryResponse getInquiry(Long id);
}
