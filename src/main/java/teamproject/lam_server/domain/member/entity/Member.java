package teamproject.lam_server.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.interaction.entity.member.Follower;
import teamproject.lam_server.domain.interaction.entity.review.ReviewCommentReact;
import teamproject.lam_server.domain.interaction.entity.review.ReviewLike;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleCommentReact;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleLike;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.constants.SocialType;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.exception.badrequest.AlreadyDropMember;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static teamproject.lam_server.constants.SessionConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseTimeEntity {

    @OneToMany(mappedBy = "to")
    private final Set<Follower> followers = new HashSet<>();
    @OneToMany(mappedBy = "from")
    private final Set<Follower> following = new HashSet<>();

    // 스케줄
    @OneToMany(mappedBy = "member")
    private final List<Schedule> schedules = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private final List<ScheduleComment> scheduleComments = new ArrayList<>();
    @OneToMany(mappedBy = "from")
    private final Set<ScheduleLike> scheduleLikes = new HashSet<>();
    @OneToMany(mappedBy = "from")
    private final Set<ScheduleCommentReact> scheduleCommentReacts = new HashSet<>();

    // 리뷰
    @OneToMany(mappedBy = "member")
    private final List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private final List<ReviewComment> reviewComments = new ArrayList<>();
    @OneToMany(mappedBy = "from")
    private final Set<ReviewLike> reviewLikes = new HashSet<>();
    @OneToMany(mappedBy = "from")
    private final Set<ReviewCommentReact> reviewCommentReacts = new HashSet<>();

    @Column(unique = true, updatable = false)
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
    private SocialType socialType;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AccountState status;

    @Formula("(select count(1) from follower f where f.to_member_id = member_id)")
    private int numberOfFollowers;
    @Formula("(select count(1) from schedule s join follower f on s.member_id = f.to_member_id where f.from_member_id = member_id)")
    private int numberOfFollows;
    @Formula("(select count(1) from review r where r.member_id = member_id)")
    private int numberOfReviews;
    @Formula("(select count(1) from schedule s where s.member_id = member_id)")
    private int numberOfSchedules;


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
        return Calendar.getInstance().get(Calendar.YEAR) - this.birth.getYear() + 1;
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
