package teamproject.lam_server.domain.inqiury.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.service.InquiryService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;
import static teamproject.lam_server.util.JwtUtil.extractAccessToken;

@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryApiController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<?> writeInquiry(
            @RequestHeader(value = "Authorization") String accessTokenRequest,
            @Valid @RequestBody InquiryEditor request) {
        inquiryService.write(extractAccessToken(accessTokenRequest), request);
        return CustomResponse.success(CREATE_INQUIRY);
    }

    @GetMapping
    public ResponseEntity<?> getInquiries(
            @RequestHeader(value = "Authorization") String accessTokenRequest,
            PageableDTO pageableDTO) {
        CustomPage<InquiryListResponse> result = inquiryService.getInquires(extractAccessToken(accessTokenRequest), pageableDTO);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInquiries(
            @PathVariable Long id) {
        InquiryResponse result = inquiryService.getInquiry(id);
        return CustomResponse.success(READ_INQUIRY, result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editInquiry(@PathVariable Long id, @Valid @RequestBody InquiryEditor editor) {
        inquiryService.edit(id, editor);
        return CustomResponse.success(UPDATE_INQUIRY);
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<?> removeInquiry(@PathVariable Long id) {
        inquiryService.remove(id);
        return CustomResponse.success(DELETE_INQUIRY);
    }
}
