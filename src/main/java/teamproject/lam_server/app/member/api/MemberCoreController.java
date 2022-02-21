package teamproject.lam_server.app.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.app.member.dto.MemberResponse;
import teamproject.lam_server.app.member.service.MemberServiceImpl;
import teamproject.lam_server.global.dto.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class MemberCoreController {
    private final Response response;
    private final MemberServiceImpl userService;

    @GetMapping("/members")
    public ResponseEntity<?> allUsers() {
        List<MemberResponse> result = userService.findAll();
        return response.success(result);
    }


    @GetMapping("/members/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        MemberResponse result = userService.findOne(id);
        return response.success(result);
    }
}
