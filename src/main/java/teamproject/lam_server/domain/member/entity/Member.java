package teamproject.lam_server.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.constants.SocialType;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewLike;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleLike;
import teamproject.lam_server.exception.badrequest.AlreadyDropMember;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static teamproject.lam_server.constants.SessionConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "loginId", "nickname", "name", "email", "gender", "birth"})
public class Member extends BaseTimeEntity {

    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    private final Set<Follower> followers = new HashSet<>();
    @OneToMany(mappedBy = "from")
    @ToString.Exclude
    private final Set<Follower> following = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private final List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    @ToString.Exclude
    private final Set<ScheduleLike> scheduleLikes = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private final List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    @ToString.Exclude
    private final Set<ReviewLike> reviewLikes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true, updatable = false)
    private String loginId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String nickname;
    private String email;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private LocalDate birth;
    private String image;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AccountState status;

    @Builder(builderClassName = "basicBuilder", builderMethodName = "basicBuilder")
    public Member(String loginId, String password, String name, String nickname, String email, GenderType gender, LocalDate birth) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.role = Role.USER;
        this.status = AccountState.NORMAL;
    }

    @Builder(builderClassName = "socialBuilder", builderMethodName = "socialBuilder")
    public Member(String email, String name, SocialType socialType) {
        this.email = email;
        this.name = name;
        this.socialType = socialType;
        this.role = Role.GUEST;
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

    public void registerBasicInfo(String password, String nickname, LocalDate birth, GenderType gender) {
        updatePassword(password);
        updateNickname(nickname);
        updateBirth(birth);
        updateGender(gender);
        conferRole(Role.USER);
    }

    /**
     * Update logic
     */
    public void updatePassword(String password) {
        this.password = password;
    }

    private void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateGender(GenderType gender) {
        this.gender = gender;
    }

    public void updateImage(String image) {
        if (image != null) this.image = image;
    }

    public void drop() {
        if (this.status == AccountState.DROP) {
            throw new AlreadyDropMember();
        }
        this.status = AccountState.DROP;
    }

    public void modifyMemberInfo(String nickname, String image) {
        updateNickname(nickname);
        updateImage(image);
    }

    public void connectSocial(SocialType socialType) {
        this.socialType = socialType;
    }

    public void conferRole(Role role) {
        this.role = role;
    }
}
