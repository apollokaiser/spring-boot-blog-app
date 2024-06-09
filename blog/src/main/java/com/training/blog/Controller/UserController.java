package com.training.blog.Controller;

import com.training.blog.Payload.Request.RePasswordRequest;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Service.User.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpHeaders headers;

    @PostMapping(value="/change-password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody @Valid RePasswordRequest request){
    ResponseMessage response = userService.changePassword(request);
    return new ResponseEntity<ResponseMessage>(response, headers, HttpStatus.OK);
    }
}
