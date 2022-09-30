package teamproject.lam_server.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.service.InquiryService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryApiController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<?> writeInquiry(@Valid @RequestBody InquiryCreate request) {
        inquiryService.write(request);
        return CustomResponse.success(CREATE_INQUIRY);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getInquiries(PageableDTO pageableDTO) {
        CustomPage<InquiryListResponse> result = inquiryService.getInquires(pageableDTO);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInquiry(@PathVariable Long id) {
        InquiryResponse result = inquiryService.getInquiry(id);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editInquiry(@PathVariable Long id, @Valid @RequestBody InquiryEdit editor) {
        inquiryService.edit(id, editor);
        return CustomResponse.success(UPDATE_INQUIRY);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Long id) {
        inquiryService.delete(id);
        return CustomResponse.success(DELETE_INQUIRY);
    }
}
