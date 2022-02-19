package teamproject.lam_server.app.customercenter.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.constants.AttrConstants;
import teamproject.lam_server.constants.CategoryConstants.CustomerCenterCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_server.constants.AttrConstants.CUSTOMER_CENTER;
import static teamproject.lam_server.constants.AttrConstants.MENU;
import static teamproject.lam_server.constants.PathConstants.CUSTOMER_CENTER_DIR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customerCenter")
public class CustomerCenterController {
    protected Map<String, Object> createMenuMap(String path, String menuName, Object menus) {
        Map<String, Object> map = new HashMap<>();
        map.put(AttrConstants.PATH, path);
        map.put(AttrConstants.MENU_NAME, menuName);
        map.put(AttrConstants.MENUS, menus);
        return map;
    }

    @GetMapping("/sideBarMenus")
    public List<Map<String,Object>> sideBarMenus() {
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(this.createMenuMap(AttrConstants.CUSTOMER_CENTER, AttrConstants.CUSTOMER_CENTER, CustomerCenterCategory.values()));
        return list;
    }


    @GetMapping
    public String customerCenter(@RequestParam(MENU) CustomerCenterCategory menu,Model model) {
        model.addAttribute(MENU, menu);
        return CUSTOMER_CENTER_DIR+CUSTOMER_CENTER;
    }
}
