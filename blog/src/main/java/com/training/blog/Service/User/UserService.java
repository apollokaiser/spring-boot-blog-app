package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.Users;
import com.training.blog.Enum.EmailTemplateEngine;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Payload.ResetPasswordRequest;
import com.training.blog.Payload.ResponseMessage;
import com.training.blog.Payload.UserCredentialsRequest;
import com.training.blog.Security.JWTAuth.JWTService;
import com.training.blog.Service.Email.EmailService;
import com.training.blog.Service.UserToken.TokenService;
import com.training.blog.Utils.RandomCodeGenerator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private EmailService emailService;
    private RandomCodeGenerator codeGenerator;
    private TokenService tokenService;
    public ResponseMessage login(UserCredentialsRequest login){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        HashMap<String, Object> data  = new HashMap<String, Object>();
        var userDetail = (MyUserDetail) auth.getPrincipal();
        var user = userDetail.getUser();
        data.put("username", user.getFullName());
        var jwt = jwtService.generateToken(data,userDetail);
    return ResponseMessage.builder()
            .status(HttpStatus.OK)
            .message("Login successfully")
            .data(new HashMap<>(){{
                put("jwt",jwt);
            }})
            .build();
    }
    //this method is used to reset the password when user forgot password
    public ResponseMessage resetPassword(String email) throws MessagingException {
        Users user = userDao.findUsersByEmail(email)
                .orElseThrow(() -> new NotFoundEntityException("User not found", Users.class));
        String subject = "RESET PASSWORD FOR YOUR ACCOUNT";
        String resetPasswordCode = codeGenerator.generateRandomCode(6);
        EmailTemplateEngine emailTemplate = EmailTemplateEngine.RESET_PASSWORD;
            emailService.sendResetPasswordCode(email,subject,resetPasswordCode,emailTemplate);
            return ResponseMessage.builder()
                    .status(HttpStatus.OK)
                    .message("Reset password code has been sent to your email")
                    .build();
    }

    public ResponseMessage resetPassword(ResetPasswordRequest request) {
        if(request.getResetCode().isEmpty()) {
            throw new NullPointerException("Reset code is empty");
        }
        Users user = userDao.findUsersByEmail(request.getEmail())
               .orElseThrow(() -> new NotFoundEntityException("User not found", Users.class));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        tokenService.validateUserToken(request.getResetCode());
        userDao.resetPassword(
                request.getEmail(),
                passwordEncoder.encode(request.getNewPassword())
        );
        return ResponseMessage.builder()
               .status(HttpStatus.OK)
               .message("Password reset successfully")
               .build();
    }
}
