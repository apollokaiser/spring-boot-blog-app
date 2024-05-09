package com.training.blog.Controller;

import com.training.blog.Entities.Users;
import com.training.blog.Payload.UserCredentialsRequest;
import com.training.blog.Payload.ResponseMessage;
import com.training.blog.Service.User.RegisterService;
import com.training.blog.Service.User.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
@AllArgsConstructor
public class AuthController {

    private final RegisterService registerService;

    private final UserService userService;

    private final HttpHeaders headers;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid UserCredentialsRequest register) throws MessagingException {
        registerService.register(Users.builder()
                        .email(register.getEmail())
                        .password(register.getPassword())
                .build());
        ResponseMessage message = ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Registration successfully")
                .build();
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
        @GetMapping(value="/authentication", params = {"c_token"})
        public ResponseEntity<?> validateAccount(
                @RequestParam(value = "c_token", required = true) String token){
            //TODO: CODING BUSINESS LOGICAL FOR VALIDATE ACCOUNT
            return ResponseEntity.ok().build();
        }
        @PostMapping(value="/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid UserCredentialsRequest login){
           ResponseMessage response =  userService.login(login);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


}
