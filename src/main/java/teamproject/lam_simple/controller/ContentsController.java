package teamproject.lam_simple.controller;

import java.util.HashMap;
import java.util.Map;

public class ContentsController extends MainController {

    protected Map<String,Object> createMenuMap(String menuName, Object menus) {
        Map<String, Object> map = new HashMap<>();
        map.put("menuName", menuName);
        map.put("menus", menus);
        return map;
    }
}