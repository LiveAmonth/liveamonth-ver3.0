package teamproject.lam_server.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.interaction.entity.member.Follower;
import teamproject.lam_server.domain.interaction.entity.review.ReviewCommentInteraction;
import teamproject.lam_server.domain.interaction.entity.review.ReviewLike;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleCommentInteraction;
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

import static teamproject.lam_server.constants.AttrConstants.DEFAULT_PROFILE_FILE_NAME;
import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseTimeEntity {

    @OneToMany(mappedBy = "to", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final Set<Follower> followers = new HashSet<>();
    @OneToMany(mappedBy = "from")
    private final Set<Follower> following = new HashSet<>();

    // 스케줄
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Schedule> schedules = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<ScheduleComment> scheduleComments = new ArrayList<>();
    @OneToMany(mappedBy = "from", orphanRemoval = true)
    private final Set<ScheduleLike> scheduleLikes = new HashSet<>();
    @OneToMany(mappedBy = "from", orphanRemoval = true)
    private final Set<ScheduleCommentInteraction> scheduleCommentInteractions = new HashSet<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Inquiry> inquiries = new ArrayList<>();

    // 리뷰
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<ReviewComment> reviewComments = new ArrayList<>();
    @OneToMany(mappedBy = "from", orphanRemoval = true)
    private final Set<ReviewLike> reviewLikes = new HashSet<>();
    @OneToMany(mappedBy = "from", orphanRemoval = true)
    private final Set<ReviewCommentInteraction> reviewCommentInteractions = new HashSet<>();

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
    private long numberOfFollowers;
    @Formula("(select count(1) from schedule s join follower f on s.member_id = f.to_member_id where f.from_member_id = member_id)")
    private long numberOfFollows;
    @Formula("(select count(1) from review r where r.member_id = member_id)")
    private long numberOfReviews;
    @Formula("(select count(1) from schedule s where s.member_id = member_id)")
    private long numberOfSchedules;
    @Formula("(select count(1) from inquiry i where i.member_id = member_id)")
    private long numberOfInquiries;


    @Builder(builderClassName = "basicBuilder", builderMethodName = "basicBuilder")
    public Member(String loginId, String password, String name, String nickname, String email, GenderType gender, LocalDate birth, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.role = role;
        this.status = AccountState.NORMAL;
        this.image = DEFAULT_PROFILE_FILE_NAME;
    }

    @Builder(builderClassName = "socialBuilder", builderMethodName = "socialBuilder")
    public Member(String email, String name, SocialType socialType) {
        this.email = email;
        this.name = name;
        this.socialType = socialType;
        this.role = Role.GUEST;
        this.status = AccountState.NORMAL;
        this.image = DEFAULT_PROFILE_FILE_NAME;
    }

    // => 비즈니스 로직
    public int calcAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - this.birth.getYear() + 1;
    }

    /**
     * Update logic
     */
    public void editProfile(ProfileEditor editor) {
        nickname = editor.getNickname();
        email = editor.getEmail();
        image = editor.getImage();
    }

    public void registerBasicInfo(OAuth2RegisterEditor editor) {
        nickname = editor.getNickname();
        password = editor.getPassword();
        birth = editor.getBirth();
        gender = editor.getGender();
        conferRole(Role.USER);
    }

    public void changePassword(PasswordEditor editor) {
        password = editor.getPassword();
    }

    public ProfileEditor.ProfileEditorBuilder toProfileEditor() {
        return ProfileEditor.builder()
                .nickname(nickname)
                .email(email)
                .image(image);
    }

    public OAuth2RegisterEditor.OAuth2RegisterEditorBuilder toOAuth2Editor() {
        return OAuth2RegisterEditor.builder()
                .nickname(nickname)
                .password(password)
                .birth(birth)
                .gender(gender);
    }

    public PasswordEditor.PasswordEditorBuilder toPasswordEditor() {
        return PasswordEditor.builder()
                .password(password);
    }

    public void drop() {
        if (this.status == AccountState.DROP) {
            throw new AlreadyDropMember();
        }
        this.status = AccountState.DROP;
    }

    public void connectSocial(SocialType socialType) {
        this.socialType = socialType;
    }

    public void conferRole(Role role) {
        this.role = role;
    }

    public String getImage() {
        return IMAGEBB_URL + image;
    }
}
