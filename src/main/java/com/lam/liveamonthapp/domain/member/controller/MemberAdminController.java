package com.lam.liveamonthapp.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.service.MemberAdminService;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;

import javax.validation.Valid;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.CREATED_MEMBER;
import static com.lam.liveamonthapp.global.constants.ResponseMessage.DELETE_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/members")
public class MemberAdminController {
    private final MemberAdminService memberAdminService;

    @PostMapping("/manager/create")
    public ResponseEntity<?> createManager(@Valid @RequestBody MemberCreate request) {
        PostIdResponse result = memberAdminService.createManager(request);
        return CustomResponse.success(CREATED_MEMBER, result);
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
