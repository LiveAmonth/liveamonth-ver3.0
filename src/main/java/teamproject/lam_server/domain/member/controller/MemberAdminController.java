package teamproject.lam_server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.service.MemberAdminService;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.CREATED_MEMBER;
import static teamproject.lam_server.global.constants.ResponseMessage.DELETE_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/members")
public class MemberAdminController {
    private final MemberAdminService memberAdminService;

    @PostMapping("/manager/create")
    public ResponseEntity<?> createManager(@Valid @RequestBody MemberCreate request) {
        memberAdminService.createManager(request);
        return CustomResponse.success(CREATED_MEMBER);
    }

    /**
     * presentation layer::my page
     * -> delete user(admin)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> cleanDeleteUser(@PathVariable Long id) {
        memberAdminService.delete(id);
        return CustomResponse.success(DELETE_MEMBER);
    }
}
