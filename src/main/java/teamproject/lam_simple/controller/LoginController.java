package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.SessionConstants;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.dto.FindIdForm;
import teamproject.lam_simple.dto.FindPwForm;
import teamproject.lam_simple.dto.LoginForm;
import teamproject.lam_simple.service.LoginService;
import teamproject.lam_simple.service.MailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    private final MailService mailService;

    @ModelAttribute("emailDomains")
    public CategoryConstants.EmailDomains[] emailDomains(){return CategoryConstants.EmailDomains.values();}

    /**
     * 로그인
     * @Param LoginForm
     * @Return loginForm
     */
    @GetMapping("/login")
    public String login(@ModelAttribute("form") LoginForm form) {
        return "login/form/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("form") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/")String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) return "login/form/loginForm";

        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("noData");
            return "login/form/loginForm";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConstants.LOGIN_USER, loginUser);
        return "redirect:" + redirectURL;
    }

    /**
     * 아이디 찾기
     * @Param FindIdForm
     * @Return findIdForm, findIdResult
     */
    @GetMapping("/findId")
    public String findId(@ModelAttribute("form") FindIdForm form) {
        return "login/form/findIdForm";
    }

    @PostMapping("/findId")
    public String findId(@Valid @ModelAttribute("form") FindIdForm form, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "login/form/findIdForm";

        User foundUser = loginService.findId(form.getName(), form.unifyEmail());

        if (foundUser == null) {
            bindingResult.reject("noData");
            return "login/form/findIdForm";
        }

        String foundLoginId = foundUser.getLoginId();


        redirectAttributes.addAttribute("foundLoginId", foundLoginId);
        return "redirect:/findIdResult";
    }
    @GetMapping("/findIdResult")
    public String findIdResult(@ModelAttribute("foundLoginId") String foundLoginId){
        return "login/result/findIdResult";
    }

    /**
     * 비밀번호 찾기
     * @Mapping findPw
     * @Param FindPwForm
     * @Return findPwForm, findPwResult
     */
    @GetMapping("/findPw")
    public String findPw(@ModelAttribute("form") FindPwForm form) {return "login/form/findPwForm";}

    @PostMapping("/findPw")
    public String findPw(@Valid @ModelAttribute("form") FindPwForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "login/form/findPwForm";

        Map<String, Object> result = loginService.findPw(form.getLoginId(), form.unifyEmail());

        if (result == null) {
            bindingResult.reject("noData");
            return "login/form/findPwForm";
        }

        mailService.sendPasswordByMail(result);

        return "login/result/findPwResult";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null) session.invalidate();
        return "redirect:/";
    }
}
