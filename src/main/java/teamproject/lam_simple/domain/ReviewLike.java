package teamproject.lam_simple.domain;

import groovy.lang.Lazy;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "review_likes")
@Getter @Setter
public class ReviewLike {

    @Id
    @GeneratedValue
    @Column(name = "review_like_id")
    private Long id;


}
