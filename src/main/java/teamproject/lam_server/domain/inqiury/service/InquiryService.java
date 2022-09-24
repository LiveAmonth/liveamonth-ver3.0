package teamproject.lam_server.domain.inqiury.service;

import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

public interface InquiryService {
    void write(String extractAccessToken, InquiryEditor request);

    CustomPage<InquiryListResponse> getInquires(String extractAccessToken, PageableDTO pageableDTO);

    InquiryResponse getInquiry(Long id);


    void edit(Long id, InquiryEditor editor);

    void delete(Long id);

}
