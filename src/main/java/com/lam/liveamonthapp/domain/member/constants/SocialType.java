package com.lam.liveamonthapp.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SocialType implements EnumMapperType {
    KAKAO("kakao", "https://kapi.kakao.com/v2/user/me"),
    NAVER("naver", "https://openapi.naver.com/v1/nid/me"),
    GOOGLE("google", "https://www.googleapis.com/oauth2/v3/userinfo");
    private final String value;
    // access token을 통해 회원의 정보를 조회할 url
    private final String userInfoUrl;

    @Override
    public String getCode() {
        return name();
    }

}
