package teamproject.lam_server.domain.auth.oauth2.userInfo;


import teamproject.lam_server.domain.member.constants.SocialType;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
    private static String NAVER_DOMAIN = "naver.com";

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
        super.socialType = SocialType.NAVER;
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        String email = (String) attributes.get("email");
        String[] split = email.split("@");
        return split[1].equals(NAVER_DOMAIN) ? email : split[0] + "@" + NAVER_DOMAIN;
    }

    @Override
    public String getImage() {
        return (String) attributes.get("profile_image");
    }

}
