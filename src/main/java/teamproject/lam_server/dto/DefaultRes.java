package teamproject.lam_server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {

    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultRes(final int statusCode, final String responseMessage){
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }
    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T data) {
        return DefaultRes.<T>builder()
                .data(data)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }


}
