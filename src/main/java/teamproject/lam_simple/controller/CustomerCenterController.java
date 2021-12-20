package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_simple.constants.AttrConstants;
import teamproject.lam_simple.constants.CategoryConstants.CustomerCenterCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.AttrConstants.CUSTOMER_CENTER;
import static teamproject.lam_simple.constants.AttrConstants.MENU;
import static teamproject.lam_simple.constants.PathConstants.CUSTOMER_CENTER_DIR;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customerCenter")
@Slf4j
public class CustomerCenterController extends ContentsController{

    @ModelAttribute("sideBarMenus")
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
