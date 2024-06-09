package com.training.blog.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.training.blog.Entities.Users;
import com.training.blog.Payload.Request.LogoutRequest;
import com.training.blog.Payload.Request.RePasswordRequest;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Payload.Request.UserCredentialsRequest;
import com.training.blog.Security.ClientInfo.GoogleClient;
import com.training.blog.Service.User.RegisterService;
import com.training.blog.Service.User.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(value = "auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final RegisterService registerService;

    private final UserService userService;

    private final HttpHeaders headers;

    private final GoogleClient googleClient;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseMessage> register(
            @Valid UserCredentialsRequest register) throws MessagingException {
        ResponseMessage response =  registerService.register(register);
        return new ResponseEntity<>(response, headers, OK);
    }

    @GetMapping(value = "/authentication")
    public ResponseEntity<?> validateAccount(
            @RequestParam(value = "token", required = true) String token) throws MessagingException {
       ResponseMessage response =  userService.activateAccount(token);
        return new ResponseEntity<>(response, headers,OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseMessage> login(
            @RequestBody @Valid UserCredentialsRequest login) {
        ResponseMessage response = userService.login(login);
        return new ResponseEntity<>(response, headers, OK);
    }

    @GetMapping(value = "/reset-password")
    public ResponseEntity<ResponseMessage> resetPassword(
            @RequestBody @Valid String email) throws MessagingException {
        ResponseMessage response = userService.resetPassword(email);
        return new ResponseEntity<>(response, headers, OK);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<ResponseMessage> resetPassword(
            @RequestBody @Valid RePasswordRequest request) {
        ResponseMessage response = userService.resetPassword(request);
        return new ResponseEntity<>(response, headers, OK);
    }

    @GetMapping(value = "/oauth2/login/auth/google")
    public ResponseEntity<ResponseMessage>
    loginWithGoogle(@RequestParam String code) {
        ResponseMessage response = userService.loginWithGoogle(code);
        return new ResponseEntity<>(response, headers, OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestBody LogoutRequest req) {
          ResponseMessage response =  userService.logout(req);
        return new ResponseEntity<>(response, headers, OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseMessage> refreshToken(@RequestBody String refreshToken){
        ResponseMessage response = userService.refreshToken(refreshToken);
        return new ResponseEntity<>(response, headers, OK);
    }
}
