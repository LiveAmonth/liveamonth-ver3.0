package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import teamproject.lam_server.constants.AttrConstants;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.SessionConstants;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.FindIdForm;
import teamproject.lam_server.app.user.dto.FindPwForm;
import teamproject.lam_server.app.user.dto.LoginForm;
import teamproject.lam_server.mail.service.MailService;
import teamproject.lam_server.app.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static teamproject.lam_server.constants.AttrConstants.FORM;
import static teamproject.lam_server.constants.AttrConstants.FOUND_LOGIN_ID;
import static teamproject.lam_server.constants.PathConstants.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;
    private final MailService mailService;

    @ModelAttribute("emailDomains")
    public CategoryConstants.EmailDomains[] emailDomains() {
        return CategoryConstants.EmailDomains.values();
    }

    /**
     * 로그인
     *
     * @Param LoginForm
     * @Return loginForm
     */
    @GetMapping("/login")
    public String login(@ModelAttribute(FORM) LoginForm form) {
        return LOGIN_DIR + FORM_DIR + LOGIN_FORM;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute(FORM) LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) return LOGIN_DIR + FORM_DIR + LOGIN_FORM;

        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject(AttrConstants.NO_DATA);
            return LOGIN_DIR + FORM_DIR + LOGIN_FORM;
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConstants.LOGIN_USER, loginUser);
        return RE_DIRECT + redirectURL;
    }

    /**
     * 아이디 찾기
     *
     * @Param FindIdForm
     * @Return findIdForm, findIdResult
     */
    @GetMapping("/findId")
    public String findId(@ModelAttribute(FORM) FindIdForm form) {
        return LOGIN_DIR + FORM_DIR + FIND_ID_FORM;
    }

    @PostMapping("/findId")
    public String findId(@Valid @ModelAttribute(FORM) FindIdForm form, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return LOGIN_DIR + FORM_DIR + FIND_ID_FORM;

        User foundUser = userService.findId(form.getName(), form.unifyEmail());

        if (foundUser == null) {
            bindingResult.reject(AttrConstants.NO_DATA);
            return LOGIN_DIR + FORM_DIR + FIND_ID_FORM;
        }

        String foundLoginId = foundUser.getLoginId();

        redirectAttributes.addAttribute(FOUND_LOGIN_ID, foundLoginId);
        return RE_DIRECT_DIR + FIND_ID_RESULT;
    }

    @GetMapping("/findIdResult")
    public String findIdResult(@ModelAttribute(FOUND_LOGIN_ID) String foundLoginId) {
        return LOGIN_DIR + RESULT_DIR + FIND_ID_RESULT;
    }

    /**
     * 비밀번호 찾기
     *
     * @Mapping findPw
     * @Param FindPwForm
     * @Return findPwForm, findPwResult
     */
    @GetMapping("/findPw")
    public String findPw(@ModelAttribute(FORM) FindPwForm form) {
        return "login/form/findPwForm";
    }

    @PostMapping("/findPw")
    public String findPw(@Valid @ModelAttribute(FORM) FindPwForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return LOGIN_DIR + FORM_DIR + FIND_PW_FORM;

        Map<String, Object> result = userService.findPw(form.getLoginId(), form.unifyEmail());

        if (result == null) {
            bindingResult.reject(AttrConstants.NO_DATA);
            return LOGIN_DIR + FORM_DIR + FIND_PW_FORM;
        }

        mailService.sendPasswordByMail(result);
        return RE_DIRECT_DIR + FIND_PW_RESULT;
    }

    @GetMapping("/findPwResult")
    public String findPwResult() {
        return LOGIN_DIR + RESULT_DIR + FIND_PW_RESULT;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return RE_DIRECT_DIR;
    }
}
