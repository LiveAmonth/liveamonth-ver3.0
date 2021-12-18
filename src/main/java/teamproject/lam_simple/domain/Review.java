package teamproject.lam_simple.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "reviews")
@Getter @Setter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column
    private String review_category;

    @Lob
    private String content;

    private LocalDateTime review_date;

    @Column
    private int view_count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
