package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.app.user.dto.CreateUserRequest;
import teamproject.lam_server.app.user.dto.CreateUserResponse;
import teamproject.lam_server.app.user.repository.UserCheckRepository;
import teamproject.lam_server.app.user.service.UserServiceImpl;
import teamproject.lam_server.global.dto.Result;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserCheckRepository userCheckRepository;
    private final UserServiceImpl userService;

    @PostMapping("/users")
    public ResponseEntity<Result> joinUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = userService.save(request);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/user-login-id/{loginId}/exists")
    public ResponseEntity<Result> checkDuplicateLoginId(@PathVariable String loginId) {
        Boolean check = userCheckRepository.existsByLoginId(loginId);
        return ResponseEntity.ok().body(new Result(check));
    }
    @GetMapping("/user-email/{emailId}/{domain}/exists")
    public ResponseEntity<Result> checkDuplicateEmail(
            @PathVariable String emailId,
            @PathVariable String domain) {
        Boolean check = userCheckRepository.existsByEmail(emailId+"@"+domain);
        return ResponseEntity.ok().body(new Result(check));
    }
    @GetMapping("/user-nickname/{nickname}/exists")
    public ResponseEntity<Result> checkDuplicateNickname(@PathVariable String nickname) {
        Boolean check = userCheckRepository.existsByNickname(nickname);
        return ResponseEntity.ok().body(new Result(check));
    }

}
