package com.lam.liveamonthapp.domain.city.entity;

import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class City extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    protected CityName name;
}
