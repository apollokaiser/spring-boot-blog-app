package com.training.blog.Exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Primary
public class AccessDeniedHandlerImpl implements AccessDeniedHandler  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            LOGGER.warn("User: {} attempted to access the protected URL: {}", auth.getName(), request.getRequestURI());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }

}
