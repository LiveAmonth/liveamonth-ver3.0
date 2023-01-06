package com.lam.liveamonthapp.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import com.lam.liveamonthapp.domain.member.constants.GenderType;

import java.time.LocalDate;

import static com.lam.liveamonthapp.constants.AttrConstants.IMAGEBB_URL;

@Getter
public class MemberProfileResponse {
    private final Long id;
    private final String loginId;
    private final String nickname;
    private final String image;
    private final String name;
    private final String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate birth;
    private final GenderType gender;
    private final long numberOfReviews;

    private final long numberOfSchedules;

    private final long numberOfFollowers;
    private final long numberOfFollows;

    public MemberProfileResponse(Long id, String loginId, String nickname, String image, String name, String email, LocalDate birth, GenderType gender, long numberOfReviews, long numberOfSchedules, long numberOfFollowers, long numberOfFollows) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.image = IMAGEBB_URL + image;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.numberOfReviews = numberOfReviews;
        this.numberOfSchedules = numberOfSchedules;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
    }
}
