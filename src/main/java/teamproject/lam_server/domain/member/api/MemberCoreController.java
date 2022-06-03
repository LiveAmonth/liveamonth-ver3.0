package teamproject.lam_server.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.dto.MemberResponse;
import teamproject.lam_server.domain.member.service.MemberServiceImpl;
import teamproject.lam_server.global.dto.CustomResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberCoreController {
    private final CustomResponse customResponse;
    private final MemberServiceImpl userService;

    @GetMapping("/members")
    public ResponseEntity<?> allUsers() {
        List<MemberResponse> result = userService.findAll();
        return customResponse.success(result);
    }


    @GetMapping("/members/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        MemberResponse result = userService.findOne(id);
        return customResponse.success(result);
    }
}
