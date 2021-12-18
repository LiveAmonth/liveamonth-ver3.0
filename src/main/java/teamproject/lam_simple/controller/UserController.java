package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.lam_simple.constants.CategoryConstants.GenderTypes;
import teamproject.lam_simple.dto.UserForm;
import teamproject.lam_simple.service.UserService;
import teamproject.lam_simple.validator.SignUpFormValidator;


import javax.validation.Valid;

import static teamproject.lam_simple.constants.CategoryConstants.EmailDomains;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;

    @ModelAttribute("genderTypes")
    public GenderTypes[] genderTypes() {
        return GenderTypes.values();
    }

    @ModelAttribute("emailDomains")
    public EmailDomains[] emailDomains() {
        return EmailDomains.values();
    }


    @GetMapping("/signUp")
    public String signUp(@ModelAttribute("userForm") UserForm userForm) {
        return "user/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user/signUp";
        signUpFormValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) return "user/signUp";
        userService.save(userForm);
        return "redirect:/";
    }
}
