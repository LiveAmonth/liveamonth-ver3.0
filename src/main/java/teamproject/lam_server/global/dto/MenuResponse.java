package teamproject.lam_server.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuResponse {

    private String title;
    private List<Object> contents = new ArrayList<>();

}
