package teamproject.lam_server.domain.interaction.entity;

import lombok.Getter;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.global.entity.BaseTimeEntity;

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
