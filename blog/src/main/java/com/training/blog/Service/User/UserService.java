package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Enum.EmailTemplateEngine;
import com.training.blog.Exception.CustomException.DataNotMatchException;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Payload.RePasswordRequest;
import com.training.blog.Payload.ResponseMessage;
import com.training.blog.Payload.UserCredentialsRequest;
import com.training.blog.Security.JWTAuth.JWTService;
import com.training.blog.Service.Email.EmailService;
import com.training.blog.Service.UserToken.TokenService;
import com.training.blog.Utils.RandomCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final EmailService emailService;
    private final RandomCodeGenerator codeGenerator;
    private final TokenService tokenService;

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
    @Transactional
    public ResponseMessage resetPassword(String email) throws MessagingException {
        Users user = userDao.findUsersByEmail(email)
                .orElseThrow(() -> new NotFoundEntityException("Your email does not exist", Users.class));
        String subject = "RESET PASSWORD FOR YOUR ACCOUNT";
        String resetPasswordCode = codeGenerator.generateRandomCode(6);
        User_Token user_token =
                User_Token.builder()
                        .token(resetPasswordCode)
                        .expiresAt(System.currentTimeMillis() + 3600000)
                        .user(Users.builder().email(email).build())
                        .build();
        //save token
        tokenService.saveUserToken(user_token);
        EmailTemplateEngine emailTemplate = EmailTemplateEngine.RESET_PASSWORD;
            emailService.sendResetPasswordCode(email,subject,resetPasswordCode,emailTemplate);
            return ResponseMessage.builder()
                    .status(HttpStatus.OK)
                    .message("Reset password code has been sent to your email")
                    .build();
    }
    
    public ResponseMessage resetPassword(RePasswordRequest request) {
        if(request.getResetCode().isEmpty()) {
            throw new NullPointerException("Reset password code is empty");
        }
        // Check existing user
       boolean isExistUser = userDao.findUsersByEmail(request.getEmail()).isPresent();
        if(!isExistUser) {
            throw new NotFoundEntityException("your email does not exist", Users.class);
        }
        // kiểm tra resetCode có phải là của email muốn thay đổi không
        tokenService.validateResetPasswordToken(request.getEmail(), request.getResetCode());
        // reset password
        userDao.resetPassword(
                request.getEmail(),
                passwordEncoder.encode(request.getNewPassword())
        );
        return ResponseMessage.builder()
               .status(HttpStatus.OK)
               .message("Password reset successfully")
               .build();
    }

    public ResponseMessage activateAccount(String token){
        String user_email = tokenService.validateUserToken(token);
        userDao.validatedUser(user_email);
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Your account has been activated")
                .build();
    }

    public ResponseMessage changePassword(RePasswordRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //check email in SecurityContextHolder and email that is requested by client
        if(!email.equals(request.getEmail()))
            throw new DataNotMatchException("Email does not match");
        boolean isExistUser = userDao.findUsersByEmail(email).isPresent();
        if(!isExistUser) throw new NotFoundEntityException("User does not exist", Users.class);
        userDao.changePassword(email, passwordEncoder.encode(request.getNewPassword()));
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Your password has been changed")
                .build();
    } {}
}
