package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Payload.Request.UserCredentialsRequest;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Service.Email.EmailService;
import com.training.blog.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    @Value("${application.security.verification_expired}")
    private long verificationExpired;

    @Transactional(rollbackOn = MessagingException.class)
    public ResponseMessage register(UserCredentialsRequest user) throws MessagingException {
        boolean isExisted  = userDao.findUsersByEmail(user.getEmail()).isPresent();
        if (isExisted) throw new AppException(BusinessHttpCode.EMAIL_REGISTERED, "User already registered");
       // Create user
        Users entity = Users.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .enabled(false)
                .build();

        // generates token
        String verificationToken = UUID.randomUUID().toString();
        User_Token token = User_Token.builder()
                .token(verificationToken)
                .expiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli())
                .user(entity)
                .build();
        HashSet<User_Token> tokens = new HashSet<>();
        tokens.add(token);
        // add token for user
        entity.setTokens(tokens);
        //save user
        userDao.save(entity);
        //send verification email
        sendVerificationEmail(user.getEmail(), verificationToken);
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Register successfully ! Please check your email")
                .build();
    }

    private void sendVerificationEmail(String mail,
                                       String confirmationToken) throws MessagingException {
        EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
        String subject = "CONFIRM ACCOUNT";
        emailService.sendActivationAccount(mail,subject,confirmationToken,viewEngine);

    }

}
