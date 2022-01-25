package teamproject.lam_server.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Result<T> {
    private int count;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-EEE HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timeStamp; //요청 시간
    private T data;

    public Result(T data) {
        this.data = data;
        if(data instanceof List) count = ((List<?>) data).size();
        else count = 1;
        this.timeStamp = LocalDateTime.now();
    }

}