package teamproject.lam_server.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.service.InquiryApiService;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

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
