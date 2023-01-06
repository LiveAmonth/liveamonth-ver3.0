package com.lam.liveamonthapp.global.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.member.entity.Member;

@Getter
@Builder
public class ProfileCardResponse {

    private String nickname;

    private String email;

    private String gender;

    private int age;

    public static ProfileCardResponse of(Member member) {
        return ProfileCardResponse.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .gender(member.getGender().getCode())
                .age(member.calcAge())
                .build();
    }
}
