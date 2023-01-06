package com.lam.liveamonthapp.domain.inqiury.service;

import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryEdit;
import com.lam.liveamonthapp.domain.inqiury.dto.response.InquiryListResponse;
import com.lam.liveamonthapp.domain.inqiury.dto.response.InquiryResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

public interface InquiryApiService {

    PostIdResponse write(InquiryCreate request);
    CustomPage<InquiryListResponse> getInquires(PageableDTO pageableDTO);

    void edit(Long id, InquiryEdit editor);

    void delete(Long id);

    InquiryResponse getInquiry(Long id);
}
