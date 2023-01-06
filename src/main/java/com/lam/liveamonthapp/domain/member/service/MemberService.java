package com.lam.liveamonthapp.domain.member.service;

import com.lam.liveamonthapp.domain.member.dto.request.*;
import com.lam.liveamonthapp.domain.member.dto.response.FindIdResponse;
import com.lam.liveamonthapp.domain.member.dto.response.MemberProfileResponse;
import com.lam.liveamonthapp.domain.member.dto.response.SimpleProfileResponse;
import com.lam.liveamonthapp.global.dto.response.BooleanCheckResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;

public interface MemberService{

    /**
     * 회원 가입
     */
    PostIdResponse signUp(MemberCreate request);

    /**
     * 이메일 중복 체크
     */
    BooleanCheckResponse checkDuplicateEmail(String email);

    /**
     * 회원 아이디 중복 체크
     */
    BooleanCheckResponse checkDuplicateLoginId(String LoginId);

    /**
     * 닉네임 중복 체크
     */
    BooleanCheckResponse checkDuplicateNickname(String nickname);

    /**
     * 회원 아이디 찾기
     */
    FindIdResponse findLoginId(MemberFindId request);

    /**
     * 회원 비밀번호 찾기
     */
    void findPassword(MemberFindPassword request);

    /**
     * 비밀번호 재확인
     */
    BooleanCheckResponse reconfirm(MemberReconfirm request);

    /**
     * 회원 정보  수정
     */
    void editProfile(ProfileEdit request);

    void changePassword(PasswordEdit request);

    /**
     * 회원 탈퇴
     */
    void dropUser();

    /**
     * 회원 정보 조회
     */
    MemberProfileResponse getMember();

    /**
     * 메인 화면 프로필
     */
    SimpleProfileResponse getSimpleProfile();
}
