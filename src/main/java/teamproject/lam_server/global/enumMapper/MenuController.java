package teamproject.lam_server.global.enumMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.global.dto.CustomResponse;

import static teamproject.lam_server.global.enumMapper.EnumClassConst.CUSTOMER_CENTER_MENU;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.MY_PAGE_MENU;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final EnumMapper enumMapper;

    @GetMapping("/my-page-menu")
    public ResponseEntity<?> getMyPageMenu() {
        return CustomResponse.getCategorySuccess(enumMapper, MY_PAGE_MENU);
    }

    @GetMapping("/customer-center-menu")
    public ResponseEntity<?> getCustomerCenterMenu() {
        return CustomResponse.getCategorySuccess(enumMapper, CUSTOMER_CENTER_MENU);
    }

}
