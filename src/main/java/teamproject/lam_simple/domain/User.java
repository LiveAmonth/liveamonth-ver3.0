package teamproject.lam_simple.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import teamproject.lam_simple.constants.CategoryConstants.GenderTypes;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static teamproject.lam_simple.constants.SessionConstants.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "loginId")
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderTypes genderTypes;

    private Date birth;

    private String image;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    @Builder
    public User(String loginId, String password, String name, String nickname, String email, GenderTypes genderTypes, Date birth) {
        Assert.notNull(loginId, "loginId must not be empty");
        Assert.notNull(password, "password must not be empty");
        Assert.notNull(name, "name must not be empty");
        Assert.notNull(nickname, "nickname must not be empty");
        Assert.notNull(email, "email must not be empty");
        Assert.notNull(genderTypes, "gender must not be null");
        Assert.notNull(birth, "birth must not be empty");
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.genderTypes = genderTypes;
        this.birth = birth;
    }

    // => 비즈니스 로직
    public int calcAge(){
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);
        int age = currentYear - this.birth.toLocalDate().getYear() + 1;
        return age;
    }

    public String getProfileImgPath(){
        if(this.image == null) return S3_BUCKET_PATH+ PROFILE_IMAGE_DIR+ DEFAULT_IMAGE_NAME;
        else return S3_BUCKET_PATH+ PROFILE_IMAGE_DIR + this.image;
    }
}
