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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserServiceImpl userService;

    /**
     * presentation layer::home, sign up
     * -> user sign up
     */
    @PostMapping("/users")
    public ResponseEntity<Result> joinUser(@RequestBody @Valid CreateUserRequest request) {
        SimpleUserResponse response = userService.save(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(new Result(response));
    }

    /**
     * presentation layer::home, login
     * -> user login
     */
    @GetMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginUserRequest request) {
        User login = userService.login(request);
        return ResponseEntity.ok().body(new Result(login));
    }

    /**
     * presentation layer::login
     * -> find user login id
     */
    @PostMapping("/users/findId")
    public ResponseEntity<Result> findLoginId(@RequestBody FindLoginIdRequest request) {
        SimpleUserResponse response = userService.findLoginId(request);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::login
     * -> find user password
     */
    @PostMapping("/users/findPw")
    public ResponseEntity<Result> findPassword(@RequestBody FindPasswordRequest request){
        FindPasswordResponse response = userService.findPassword(request);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::my page
     * -> modify user information
     */
    @PostMapping("/users/{id}/modify")
    public ResponseEntity<Result> modifyUser(
            @PathVariable Long id,
            @RequestBody ModifyUserRequest request) {
        Long modifiedId = userService.modify(id, request);
        return ResponseEntity.ok().body(new Result(modifiedId));
    }

    /**
     * presentation layer::my page
     * -> drop user(customer)
     */
    @PostMapping("/users/{id}/drop")
    public ResponseEntity<Result> dropUser(@PathVariable Long id) {
        SimpleUserResponse response = userService.dropUser(id);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::my page
     * -> delete user(admin)
     */
    @DeleteMapping("/users/{id}/clean-delete")
    public ResponseEntity<Result> cleanDeleteUser(@PathVariable Long id) {
        SimpleUserResponse response = userService.delete(id);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::sign up
     * -> login id duplicate check
     */
    @GetMapping("/user-login-id/{loginId}/exists")
    public ResponseEntity<Result> checkDuplicateLoginId(@PathVariable String loginId) {
        DuplicateCheckResponse response = userService.checkDuplicateLoginId(loginId);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::sign up
     * -> email(id & domain) duplicate check
     */
    @GetMapping("/user-email/{emailId}/{domain}/exists")
    public ResponseEntity<Result> checkDuplicateEmail(
            @PathVariable String emailId,
            @PathVariable String domain) {
        DuplicateCheckResponse response = userService.checkDuplicateEmail(emailId,domain);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::sign up
     * -> nickname duplicate check
     */
    @GetMapping("/user-nickname/{nickname}/exists")
    public ResponseEntity<Result> checkDuplicateNickname(@PathVariable String nickname) {
        DuplicateCheckResponse response = userService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok().body(new Result(response));
    }



}
