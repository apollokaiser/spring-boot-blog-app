package com.training.blog.Service.Email;

import com.training.blog.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;

public interface EmailService {
   public void sendActivationAccount(
            String to,
            String subject,
            String activationCode,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
}
