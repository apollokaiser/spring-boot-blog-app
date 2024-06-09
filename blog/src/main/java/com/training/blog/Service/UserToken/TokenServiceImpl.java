package com.training.blog.Service.UserToken;

import com.training.blog.DAO.Token.TokenDao;
import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Enum.EmailTemplateEngine;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.TokenExpiredException;
import com.training.blog.Service.Email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Lazy
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenDao tokenDao;

    private final EmailService emailService;

    @Value("${application.security.verification_expired}")
    private long verificationExpired;
    @Override
    public User_Token findUserToken(String token) {
        return null;
    }

    @Override
    public void saveUserToken(User_Token user_token) {
        tokenDao.save(user_token);
    }

    @Override
    @Transactional
    public String validateUserToken(String token) throws MessagingException {
        User_Token user_token = tokenDao.findByToken(token)
                .orElseThrow(() -> new AppException(BusinessHttpCode.INVALID_TOKEN));
        Date expiration = new Date(user_token.getExpiresAt());
        if(expiration.before(new Date())){
            EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
            String subject = "CONFIRM ACCOUNT";
            String verificationToken = UUID.randomUUID().toString();
            emailService.sendActivationAccount(user_token.getUser().getEmail(),
                    subject,verificationToken,viewEngine);
            User_Token entity = User_Token.builder()
                    .user(user_token.getUser())
                    .token(verificationToken)
                    .expiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli())
                    .build();
            tokenDao.save(entity);
            throw new AppException(BusinessHttpCode.EXPIRED_TOKEN,"Token has expired ! Check new token in your mail");
        }
        return tokenDao.validatedToken(token);
    }

    @Override
    public void validateResetPasswordToken(String email, String token) {
       tokenDao.validateResetPasswordToken(email, token);
    }
}
