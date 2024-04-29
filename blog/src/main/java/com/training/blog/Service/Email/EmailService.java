package com.training.blog.Service.Email;

import com.training.blog.Utils.EmailTemplateEngine;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

public interface EmailService {
   public void sendActivationAccount(
            String to,
            String subject,
            String activationCode,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
}
