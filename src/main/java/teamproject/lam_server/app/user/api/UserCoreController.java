package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.app.user.dto.UserResponse;
import teamproject.lam_server.app.user.service.UserServiceImpl;
import teamproject.lam_server.global.dto.Result;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class UserCoreController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<Result> allUsers() {
        List<UserResponse> responses = userService.findAll();
        return ResponseEntity.ok().body(new Result(responses));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Result> findUser(@PathVariable Long id) {
        UserResponse response = userService.findOne(id);
        return ResponseEntity.ok().body(new Result(response));
    }
}
