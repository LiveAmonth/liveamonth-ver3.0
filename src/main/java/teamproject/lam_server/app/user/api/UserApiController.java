package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.*;
import teamproject.lam_server.app.user.repository.UserCheckRepository;
import teamproject.lam_server.app.user.service.UserServiceImpl;
import teamproject.lam_server.global.dto.Result;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserCheckRepository userCheckRepository;
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<Result> allUsers() {
        List<UserResponse> responses = userService.findAll();
        return ResponseEntity.ok().body(new Result(responses));
    }

    @PostMapping("/users")
    public ResponseEntity<Result> joinUser(@RequestBody @Valid CreateUserRequest request) {
        SimpleUserResponse response = userService.save(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(new Result(response));
    }

    @GetMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginUserRequest request) {
        User login = userService.login(request);
        return ResponseEntity.ok().body(new Result(login));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Result> findUser(@PathVariable Long id) {
        UserResponse response = userService.findOne(id);
        return ResponseEntity.ok().body(new Result(response));
    }

    @PostMapping("/users/{id}/modify")
    public ResponseEntity<Result> modifyUser(
            @PathVariable Long id,
            @RequestBody ModifyUserRequest request) {
        Long modifiedId = userService.modify(id, request);
        return ResponseEntity.ok().body(new Result(modifiedId));
    }


    @PostMapping("/users/{id}/drop")
    public ResponseEntity<Result> dropUser(@PathVariable Long id) {
        SimpleUserResponse response = userService.dropUser(id);
        return ResponseEntity.ok().body(new Result(response));
    }

    @DeleteMapping("/users/{id}/clean-delete")
    public ResponseEntity<Result> cleanDeleteUser(@PathVariable Long id) {
        SimpleUserResponse response = userService.delete(id);
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
        Boolean check = userCheckRepository.existsByEmail(emailId + "@" + domain);
        return ResponseEntity.ok().body(new Result(check));
    }

    @GetMapping("/user-nickname/{nickname}/exists")
    public ResponseEntity<Result> checkDuplicateNickname(@PathVariable String nickname) {
        Boolean check = userCheckRepository.existsByNickname(nickname);
        return ResponseEntity.ok().body(new Result(check));
    }

}
