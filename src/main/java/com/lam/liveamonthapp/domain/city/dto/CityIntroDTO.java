package com.lam.liveamonthapp.domain.city.dto;

import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityIntroDTO {
    @NotNull
    private CityName name;

    @NotNull
    private CityIntroCategory category;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "지원하지 않는 확장자입니다.")
    private String image;
}
