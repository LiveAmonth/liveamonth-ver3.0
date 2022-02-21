package teamproject.lam_server.global.service;

import org.springframework.stereotype.Service;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.global.dto.MenuResponse;
import teamproject.lam_server.global.dto.Response;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static teamproject.lam_server.constants.AttrConstants.*;
import static teamproject.lam_server.constants.AttrConstants.INQUIRY;
import static teamproject.lam_server.constants.CategoryConstants.*;
import static teamproject.lam_server.constants.PathConstants.MY_PAGE;

@Service

public class MenuService {
    public MenuResponse getCityMenus(){
        List<String> cityMenus =
                Arrays.stream(CityName.values())
                        .map(CityName::getValue)
                        .collect(toList());
        return new MenuResponse("cityMenus", Collections.singletonList(cityMenus));
    }

    public MenuResponse getMyPageMenus(){
        List<MenuResponse> middleMenus = new ArrayList<>();
        middleMenus.add(new MenuResponse(ACCOUNT, menuToList(ACCOUNT)));
        middleMenus.add(new MenuResponse(COMMUNITY, menuToList(COMMUNITY)));
        middleMenus.add(new MenuResponse(INQUIRY, menuToList(INQUIRY)));
        return new MenuResponse("myPage", Collections.singletonList(middleMenus));
    }

    private List<Object> menuToList(String middleTitle) {
        return Arrays.stream(MyPageCategory.values())
                .filter(t -> t.getCategory().equals(middleTitle))
                .collect(toList());
    }
}
