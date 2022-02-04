package teamproject.lam_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/connectTest")
    public String Test(){
        return "스프링 부트&뷰 연동 테스트";
    }
}
