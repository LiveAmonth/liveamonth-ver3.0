package com.lam.liveamonthapp.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.member.dto.request.*;
import com.lam.liveamonthapp.domain.member.dto.response.FindIdResponse;
import com.lam.liveamonthapp.domain.member.dto.response.MemberProfileResponse;
import com.lam.liveamonthapp.domain.member.dto.response.SimpleProfileResponse;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.entity.PasswordEditor;
import com.lam.liveamonthapp.domain.member.entity.ProfileEditor;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.domain.member.repository.query.MemberQueryRepository;
import com.lam.liveamonthapp.exception.notfound.MemberNotFound;
import com.lam.liveamonthapp.global.dto.response.BooleanCheckResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.mail.dto.TempPasswordSendMailInfo;
import com.lam.liveamonthapp.mail.service.MailService;
import com.lam.liveamonthapp.util.JwtUtil;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final SecurityContextFinder finder;
    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    @Transactional
    public PostIdResponse signUp(MemberCreate request) {
        Member saveMember = memberRepository.save(request.toEntity(passwordEncoder));
        return PostIdResponse.of(saveMember.getId());
    }

    @Override
    public MemberProfileResponse getMember() {
        return memberQueryRepository.getMemberProfile(finder.getAuthenticationName()).
                orElseThrow(MemberNotFound::new);
    }

    @Override
    public SimpleProfileResponse getSimpleProfile() {
        return memberQueryRepository.getSimpleProfile(finder.getAuthenticationName())
                .orElseThrow(MemberNotFound::new);
    }

    @Override
    public BooleanCheckResponse reconfirm(MemberReconfirm request) {
        return passwordEncoder.matches(request.getPassword(), finder.getLoggedInMember().getPassword())
                ? BooleanCheckResponse.of(true, SUCCESS_RECONFIRM)
                : BooleanCheckResponse.of(false, FAIL_RECONFIRM);
    }

    @Override
    @Transactional
    public void editProfile(ProfileEdit request) {
        Member member = finder.getLoggedInMember();

        ProfileEditor editor = member.toProfileEditor()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .build();

        member.editProfile(editor);
    }

    @Override
    @Transactional
    public void changePassword(PasswordEdit request) {
        Member member = finder.getLoggedInMember();

        PasswordEditor editor = member.toPasswordEditor()
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        member.changePassword(editor);
    }

    @Override
    @Transactional
    public void dropUser() {
        finder.getLoggedInMember().drop();
    }

    @Override
    public FindIdResponse findLoginId(MemberFindId request) {
        return memberQueryRepository.findLoginId(request).orElseThrow(MemberNotFound::new);
    }

    @Override
    @Transactional
    public void findPassword(MemberFindPassword request) {
        // find user with request
        Member member = memberRepository.findByLoginIdAndEmail(request.getLoginId(), request.getEmail())
                .orElseThrow(MemberNotFound::new);

        // create random password
        String tempPassword = JwtUtil.createRandomPassword();

        // update password (temporary password)
        PasswordEditor editor = member.toPasswordEditor()
                .password(passwordEncoder.encode(tempPassword))
                .build();

        member.changePassword(editor);

        // mail send
        mailService.sendMail(TempPasswordSendMailInfo.of(member), tempPassword);
    }

    @Override
    public BooleanCheckResponse checkDuplicateEmail(String email) {
        Boolean isDuplicated = memberRepository.existsByEmail(email);
        return isDuplicated
                ? BooleanCheckResponse.of(false, DUPLICATE_EMAIL)
                : BooleanCheckResponse.of(true, AVAILABLE_EMAIL);
    }

    @Override
    public BooleanCheckResponse checkDuplicateLoginId(String LoginId) {
        Boolean isDuplicated = memberRepository.existsByLoginId(LoginId);
        return isDuplicated
                ? BooleanCheckResponse.of(false, DUPLICATE_LOGIN_ID)
                : BooleanCheckResponse.of(true, AVAILABLE_LOGIN_ID);
    }

    @Override
    public BooleanCheckResponse checkDuplicateNickname(String nickname) {
        Boolean isDuplicated = memberRepository.existsByNickname(nickname);
        return isDuplicated
                ? BooleanCheckResponse.of(false, DUPLICATE_NICKNAME)
                : BooleanCheckResponse.of(true, AVAILABLE_NICKNAME);
    }
}
