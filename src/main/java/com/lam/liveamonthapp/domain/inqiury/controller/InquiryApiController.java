package com.lam.liveamonthapp.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryEdit;
import com.lam.liveamonthapp.domain.inqiury.dto.response.InquiryListResponse;
import com.lam.liveamonthapp.domain.inqiury.dto.response.InquiryResponse;
import com.lam.liveamonthapp.domain.inqiury.service.InquiryApiService;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import javax.validation.Valid;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryApiController {

    private final InquiryApiService inquiryApiService;

    @PostMapping
    public ResponseEntity<?> writeInquiry(@Valid @RequestBody InquiryCreate request) {
        PostIdResponse result = inquiryApiService.write(request);
        return CustomResponse.success(CREATE_INQUIRY, result);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getInquiries(PageableDTO pageableDTO) {
        CustomPage<InquiryListResponse> result = inquiryApiService.getInquires(pageableDTO);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInquiry(@PathVariable Long id) {
        InquiryResponse result = inquiryApiService.getInquiry(id);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editInquiry(@PathVariable Long id, @Valid @RequestBody InquiryEdit editor) {
        inquiryApiService.edit(id, editor);
        return CustomResponse.success(UPDATE_INQUIRY);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Long id) {
        inquiryApiService.delete(id);
        return CustomResponse.success(DELETE_INQUIRY);
    }
}
