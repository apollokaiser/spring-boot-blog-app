package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Service.Email.EmailService;
import com.training.blog.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    @Transactional
    public void register(Users user) throws MessagingException {
        boolean isExisted  = userDao.findUsersByEmail(user.getEmail()).isPresent();
        if (isExisted) throw new EntityExistsException("User already registered");
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
                .expiresAt(System.currentTimeMillis() + 3600000)
                .user(entity)
                .build();
        Set<User_Token> tokens = new HashSet<>(){{
            add(token);
        }};
        // add token for user
        entity.setTokens(tokens);
        //save user
        userDao.save(entity);
        //send verification email
        sendVerificationEmail(user.getEmail(), verificationToken);

    }
    private void sendVerificationEmail(String mail,
                                       String confirmationToken) throws MessagingException {
        EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
        String subject = "CONFIRM ACCOUNT";
        emailService.sendActivationAccount(mail,subject,confirmationToken,viewEngine);

    }

}
