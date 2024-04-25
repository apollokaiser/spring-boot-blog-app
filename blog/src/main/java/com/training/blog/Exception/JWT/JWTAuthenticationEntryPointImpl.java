package com.training.blog.Exception.JWT;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JWTAuthenticationEntryPointImpl implements JWTAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (request.getHeader("authorization") == null)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
