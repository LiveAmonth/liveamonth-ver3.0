package teamproject.lam_server.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.Assert;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.exception.AlreadyDropUserException;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewReply;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static teamproject.lam_server.constants.SessionConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "loginId", "nickname", "name", "email", "gender", "birth"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"loginId"}))
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDate birth;

    private String image;

    @Enumerated(EnumType.STRING)
    private AccountState status;

    /**
     * 아래의 리스트들이 사실상 필요없다.
     * 어떤 회원이 작성한 게시글들의 관점으로 보는 것이 아니라
     * 게시글을 작성할 때 회원 정보가 필요한 것.
     * 사람의 입장이 아니라 시스템의 입장에서 생각해보자
     * -> 비즈니스 로직 어떤 회원이 작성한 게시글을 찾을 때
     * user.getReviews()가 아니라 reviewRepository.findByUser(user) 로 찾자!!
     */
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<ReviewReply> reviewReplies = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String name, String nickname, String email, GenderType gender, LocalDate birth) {
        Assert.notNull(loginId, "loginId must not be empty");
        Assert.notNull(password, "password must not be empty");
        Assert.notNull(name, "name must not be empty");
        Assert.notNull(nickname, "nickname must not be empty");
        Assert.notNull(email, "email must not be empty");
        Assert.notNull(gender, "gender must not be null");
        Assert.notNull(birth, "birth must not be empty");
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.status = AccountState.NORMAL;
    }

    // => 비즈니스 로직
    public int calcAge() {
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        int age = currentYear - this.birth.getYear() + 1;
        return age;
    }

    public String getProfileImgPath() {
        if (this.image == null) return S3_BUCKET_PATH + PROFILE_IMAGE_DIR + DEFAULT_IMAGE_NAME;
        else return S3_BUCKET_PATH + PROFILE_IMAGE_DIR + this.image;
    }

    /**
     * Update logic
     */
    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        if (nickname != null) this.nickname = nickname;
    }

    public void updateImage(String image) {
        if (image != null) this.image = image;
    }

    public void drop() {
        if (this.status == AccountState.DROP) {
            throw new AlreadyDropUserException(id);
        }
        this.status = AccountState.DROP;
    }

    public void modifyMemberInfo(String nickname, String image) {
        updateNickname(nickname);
        updateImage(image);
    }
}
