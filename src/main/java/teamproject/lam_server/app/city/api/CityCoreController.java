package teamproject.lam_server.app.city.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.app.city.service.core.CityCoreService;
import teamproject.lam_server.global.dto.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityCoreController {

    private final CityCoreService cityCoreService;

//    @GetMapping("/infos")
//    public ResponseEntity<Result> findCityInfos(){
//
//        return ResponseEntity.ok().body(new Result());
//    }

}
