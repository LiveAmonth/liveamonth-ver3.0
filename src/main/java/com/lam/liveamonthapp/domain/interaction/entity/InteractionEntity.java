package com.lam.liveamonthapp.domain.interaction.entity;

import lombok.Getter;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import static javax.persistence.EnumType.STRING;

@Getter
@MappedSuperclass
public abstract class InteractionEntity extends BaseTimeEntity {

    @Enumerated(STRING)
    protected InteractionState state;

    public abstract Long getCommentId();
}
