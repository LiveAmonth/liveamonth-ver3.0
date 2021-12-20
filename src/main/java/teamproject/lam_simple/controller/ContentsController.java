package teamproject.lam_simple.controller;

import teamproject.lam_simple.constants.AttrConstants;

import java.util.HashMap;
import java.util.Map;

import static teamproject.lam_simple.constants.AttrConstants.MENU_NAME;
import static teamproject.lam_simple.constants.AttrConstants.PATH;

public class ContentsController extends MainController {

    protected Map<String,Object> createMenuMap(String path, String menuName, Object menus) {
        Map<String, Object> map = new HashMap<>();
        map.put(AttrConstants.PATH, path);
        map.put(AttrConstants.MENU_NAME, menuName);
        map.put(AttrConstants.MENUS, menus);
        return map;
    }
}