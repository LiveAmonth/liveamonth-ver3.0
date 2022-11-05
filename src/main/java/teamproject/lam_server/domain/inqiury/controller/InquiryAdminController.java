package teamproject.lam_server.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerEdit;
import teamproject.lam_server.domain.inqiury.service.InquiryAdminService;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequestMapping("/admin/v1/inquiries")
@RequiredArgsConstructor
public class InquiryAdminController {

    private final InquiryAdminService inquiryAdminService;

    @PostMapping("/{inquiryId}")
    public ResponseEntity<?> answerInquiry(@PathVariable Long inquiryId, @Valid @RequestBody InquiryAnswerCreate request) {
        inquiryAdminService.answer(inquiryId, request);
        return CustomResponse.success(CREATE_INQUIRY_ANSWER);
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity<?> editInquiry(@PathVariable Long answerId, @Valid @RequestBody InquiryAnswerEdit editor) {
        inquiryAdminService.edit(answerId, editor);
        return CustomResponse.success(UPDATE_INQUIRY);
    }
}
