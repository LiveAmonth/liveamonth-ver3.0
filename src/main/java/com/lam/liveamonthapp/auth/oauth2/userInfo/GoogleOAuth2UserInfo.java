package com.lam.liveamonthapp.auth.oauth2.userInfo;

import com.lam.liveamonthapp.domain.member.constants.SocialType;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        super.socialType = SocialType.GOOGLE;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImage() {
        return (String) attributes.get("picture");
    }

}
