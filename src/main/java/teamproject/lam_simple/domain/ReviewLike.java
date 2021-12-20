package teamproject.lam_simple.domain;

import groovy.lang.Lazy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "review_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {

    @Id
    @GeneratedValue
    @Column(name = "review_like_id")
    private Long id;


}
