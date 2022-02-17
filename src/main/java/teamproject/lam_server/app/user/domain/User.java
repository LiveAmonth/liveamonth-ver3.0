package teamproject.lam_server.app.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.Assert;
import teamproject.lam_server.app.review.domain.Review;
import teamproject.lam_server.app.review.domain.ReviewReply;
import teamproject.lam_server.app.schedule.domain.Schedule;
import teamproject.lam_server.app.user.converter.GenderTypeConverter;
import teamproject.lam_server.app.user.exception.AlreadyDropUserException;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;
import teamproject.lam_server.constants.CategoryConstants.UserStatus;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static teamproject.lam_server.constants.SessionConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","loginId","nickname","name","email","gender","birth"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"loginId"}))
public class User extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;

    @Convert(converter = GenderTypeConverter.class)
    private GenderTypes gender;

    private LocalDate birth;

    private String image;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /**
     * 아래의 리스트들이 사실상 필요없다.
     * 어떤 회원이 작성한 게시글들의 관점으로 보는 것이 아니라
     * 게시글을 작성할 때 회원 정보가 필요한 것.
     * 사람의 입장이 아니라 시스템의 입장에서 생각해보자
     * -> 비즈니스 로직 어떤 회원이 작성한 게시글을 찾을 때
     *    user.getReviews()가 아니라 reviewRepository.findByUser(user) 로 찾자!!
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ReviewReply> reviewReplies = new ArrayList<>();

    @Builder
    public User(String loginId, String password, String name, String nickname, String email, GenderTypes gender, LocalDate birth) {
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
        this.status = UserStatus.NORMAL;
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
    public void updatePassword(String password){
        this.password = password;
    }

    public void updateNickname(String nickname) {
        if (nickname != null) this.nickname = nickname;
    }

    public void updateImage(String image) {
        if (image != null) this.image = image;
    }

    public void drop(){
        if(this.status == UserStatus.DROP){
            throw new AlreadyDropUserException(id);
        }
        this.status = UserStatus.DROP;
    }

    public void modifyUserInfo(String nickname, String image) {
        updateNickname(nickname);
        updateImage(image);
    }
}
