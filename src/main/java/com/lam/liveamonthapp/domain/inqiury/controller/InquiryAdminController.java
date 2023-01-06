package com.lam.liveamonthapp.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerEdit;
import com.lam.liveamonthapp.domain.inqiury.service.InquiryAdminService;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;

import javax.validation.Valid;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

@RestController
@RequestMapping("/admin/v1/inquiries")
@RequiredArgsConstructor
public class InquiryAdminController {

    private final InquiryAdminService inquiryAdminService;

    @PostMapping("/{inquiry_id}")
    public ResponseEntity<?> answerInquiry(@PathVariable("inquiry_id") Long inquiryId, @Valid @RequestBody InquiryAnswerCreate request) {
        inquiryAdminService.answer(inquiryId, request);
        return CustomResponse.success(CREATE_INQUIRY_ANSWER);
    }

    @PatchMapping("/{answer_id}")
    public ResponseEntity<?> editInquiry(@PathVariable("answer_id") Long answerId, @Valid @RequestBody InquiryAnswerEdit editor) {
        inquiryAdminService.edit(answerId, editor);
        return CustomResponse.success(UPDATE_INQUIRY);
    }
}
