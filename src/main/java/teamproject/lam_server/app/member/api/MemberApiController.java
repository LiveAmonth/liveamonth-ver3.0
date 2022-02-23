package teamproject.lam_server.app.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.member.dto.*;
import teamproject.lam_server.app.member.dto.login.LoginMemberRequest;
import teamproject.lam_server.app.member.dto.login.LogoutMemberRequest;
import teamproject.lam_server.app.member.dto.login.ReissueTokenRequest;
import teamproject.lam_server.app.member.dto.login.TokenResponse;
import teamproject.lam_server.app.member.service.LoginServiceImpl;
import teamproject.lam_server.app.member.service.MemberServiceImpl;
import teamproject.lam_server.auth.JwtTokenProvider;
import teamproject.lam_server.global.dto.MenuResponse;
import teamproject.lam_server.global.dto.Response;
import teamproject.lam_server.global.service.MenuService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
@Slf4j
public class MemberApiController {
    private final Response response;

    private final MemberServiceImpl memberService;
    private final MenuService menuService;
    private final LoginServiceImpl loginService;

    private JwtTokenProvider jwtTokenProvider;
    /**
     * presentation layer::my page
     * -> my page menus
     */
    @GetMapping("/my-page/menus")
    public ResponseEntity<?> getMyPageMenus(){
        MenuResponse result = menuService.getMyPageMenus();
        return response.success(result);
    }

    /**
     * presentation layer::home, sign up
     * -> user sign up
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> joinUser(@Valid @RequestBody CreateMemberRequest request) {
        SimpleMemberResponse result = memberService.save(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return response.success(result);
    }

    /**
     * presentation layer::home, login
     * -> user login
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginMemberRequest request) {
        TokenResponse result = loginService.login(request);

        return response.success(result, "Login Success!!", HttpStatus.OK);
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<?> reissue(@Valid @RequestBody ReissueTokenRequest request
                                     ) {
        TokenResponse result = loginService.reissue(request);
        return response.success(result, "Reissue Token!!", HttpStatus.OK);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutMemberRequest request){
        loginService.logout(request);
        return response.success("Logout Success");
    }

    /**
     * presentation layer::login
     * -> find user login id
     */
    @PostMapping("/find-id")
    public ResponseEntity<?> findLoginId(@Valid @RequestBody FindLoginIdRequest request) {
        SimpleMemberResponse result = memberService.findLoginId(request);
        return response.success(result);
    }

    /**
     * presentation layer::login
     * -> find user password
     */
    @PostMapping("/find-pw")
    public ResponseEntity<?> findPassword(@Valid @RequestBody FindPasswordRequest request){
        FindPasswordResponse result = memberService.findPassword(request);
        return response.success(result);
    }

    /**
     * presentation layer::my page
     * -> modify user information
     */
    @PostMapping("/members/{id}/modify")
    public ResponseEntity<?> modifyUser(
            @PathVariable Long id,
            @Valid @RequestBody ModifyMemberRequest request) {
        Long result = memberService.modify(id, request);
        return response.success(result);
    }

    /**
     * presentation layer::my page
     * -> drop user(customer)
     */
    @PostMapping("/members/{id}/drop")
    public ResponseEntity<?> dropUser(@PathVariable Long id) {
        SimpleMemberResponse result = memberService.dropUser(id);
        return response.success(result);
    }

    /**
     * presentation layer::my page
     * -> delete user(admin)
     */
    @DeleteMapping("/members/{id}/clean-delete")
    public ResponseEntity<?> cleanDeleteUser(@PathVariable Long id) {
        SimpleMemberResponse result = memberService.delete(id);
        return response.success(result);
    }

    /**
     * presentation layer::sign up
     * -> login id duplicate check
     */
    @GetMapping("/member-login-id/{loginId}/exists")
    public ResponseEntity<?> checkDuplicateLoginId(@PathVariable String loginId) {
        DuplicateCheckResponse result = memberService.checkDuplicateLoginId(loginId);
        return result.getCondition()
                ? response.success(result,"사용 가능", HttpStatus.OK)
                : response.success(result,"사용중인 아이디", HttpStatus.OK);
    }

    /**
     * presentation layer::sign up
     * -> email(id & domain) duplicate check
     */
    @GetMapping("/member-email/{emailId}/{domain}/exists")
    public ResponseEntity<?> checkDuplicateEmail(
            @PathVariable String emailId,
            @PathVariable String domain) {
        DuplicateCheckResponse result = memberService.checkDuplicateEmail(emailId,domain);
        return result.getCondition()
                ? response.success(result,"사용 가능", HttpStatus.OK)
                : response.success(result,"사용중인 이메일", HttpStatus.OK);
    }

    /**
     * presentation layer::sign up
     * -> nickname duplicate check
     */
    @GetMapping("/member-nickname/{nickname}/exists")
    public ResponseEntity<?> checkDuplicateNickname(@PathVariable String nickname) {
        DuplicateCheckResponse result = memberService.checkDuplicateNickname(nickname);
        return result.getCondition()
                ? response.success(result,"사용 가능", HttpStatus.OK)
                : response.success(result,"사용중인 닉네임", HttpStatus.OK);
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
