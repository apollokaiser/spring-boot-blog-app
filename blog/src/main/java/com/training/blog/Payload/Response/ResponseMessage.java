package com.training.blog.Payload.Response;

import com.training.blog.Enum.BusinessHttpCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage {
    private int status;
    private String message;
    private Map<String, Object> data;
    // only used for exceptions
    @Builder
    public ResponseMessage(HttpStatus status, String message, Map<String, Object> data)
    {
        this.status = status.value();
        this.message = message;
        this.data = data;

    }
    @Builder(builderMethodName = "errorBuilder")
    public ResponseMessage(BusinessHttpCode errorCode, String message)
    {
        this.status = errorCode.getStatus();
        if(message.isEmpty()){
        this.message = errorCode.getDescription();
        } else this.message = message;
    }
}
