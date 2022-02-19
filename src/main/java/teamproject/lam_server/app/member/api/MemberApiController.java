package teamproject.lam_server.app.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.member.dto.*;
import teamproject.lam_server.app.member.dto.login.LoginUserRequest;
import teamproject.lam_server.app.member.service.MemberServiceImpl;
import teamproject.lam_server.global.dto.MenuResponse;
import teamproject.lam_server.global.dto.Result;
import teamproject.lam_server.global.service.MenuService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
@Slf4j
public class MemberApiController {
    private final MemberServiceImpl userService;
    private final MenuService menuService;

    /**
     * presentation layer::my page
     * -> my page menus
     */
    @GetMapping("/my-page/menus")
    public ResponseEntity<Result> getMyPageMenus(){
        MenuResponse response = menuService.getMyPageMenus();
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::home, sign up
     * -> user sign up
     */
    @PostMapping("/users")
    public ResponseEntity<Result> joinUser(@RequestBody @Valid CreateMemberRequest request) {
        SimpleMemberResponse response = userService.save(request);
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
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginUserRequest request) {
        Member login = userService.login(request);
        return ResponseEntity.ok().body(new Result(login));
    }

    /**
     * presentation layer::login
     * -> find user login id
     */
    @PostMapping("/users/findId")
    public ResponseEntity<Result> findLoginId(@RequestBody FindLoginIdRequest request) {
        SimpleMemberResponse response = userService.findLoginId(request);
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
            @RequestBody ModifyMemberRequest request) {
        Long modifiedId = userService.modify(id, request);
        return ResponseEntity.ok().body(new Result(modifiedId));
    }

    /**
     * presentation layer::my page
     * -> drop user(customer)
     */
    @PostMapping("/users/{id}/drop")
    public ResponseEntity<Result> dropUser(@PathVariable Long id) {
        SimpleMemberResponse response = userService.dropUser(id);
        return ResponseEntity.ok().body(new Result(response));
    }

    /**
     * presentation layer::my page
     * -> delete user(admin)
     */
    @DeleteMapping("/users/{id}/clean-delete")
    public ResponseEntity<Result> cleanDeleteUser(@PathVariable Long id) {
        SimpleMemberResponse response = userService.delete(id);
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


//    @PostMapping("/editProfileImage")
//    public String editProfileImage(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser, @RequestPart(FILE_NAME) MultipartFile mFile) throws Exception {
//        log.info("fileNAme = {}",mFile.getOriginalFilename());
//        if (loginUser.getImage() != null) {
//            log.info("UserImageName = {}",loginUser.getImage());
//            uploader.delete(PROFILE_IMAGE_DIR + loginUser.getImage());
//        }
//        String saveName = uploader.uploadProfile(loginUser.getLoginId(), PROFILE_IMAGE_DIR, mFile.getOriginalFilename(), mFile.getBytes());
//        userWebService.editUserImage(loginUser.getId(), saveName);
//
//        return RE_DIRECT_DIR+MY_PAGE;
//    }

}
