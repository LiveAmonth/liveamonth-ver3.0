package teamproject.lam_server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.service.MemberService;
import teamproject.lam_server.global.dto.CustomResponse;

import static teamproject.lam_server.global.constants.ResponseMessage.DELETE_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/members")
public class MemberCoreController {
    private final MemberService memberService;

    /**
     * presentation layer::my page
     * -> delete user(admin)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> cleanDeleteUser(@PathVariable Long id) {
        memberService.delete(id);
        return CustomResponse.success(DELETE_MEMBER);
    }
}
