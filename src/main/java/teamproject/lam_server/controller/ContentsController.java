package teamproject.lam_server.controller;

import teamproject.lam_server.constants.AttrConstants;

import java.util.HashMap;
import java.util.Map;

public class ContentsController {

    protected Map<String, Object> createMenuMap(String path, String menuName, Object menus) {
        Map<String, Object> map = new HashMap<>();
        map.put(AttrConstants.PATH, path);
        map.put(AttrConstants.MENU_NAME, menuName);
        map.put(AttrConstants.MENUS, menus);
        return map;
    }
}