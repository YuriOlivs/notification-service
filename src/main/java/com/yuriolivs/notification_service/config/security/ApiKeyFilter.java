package com.yuriolivs.notification_service.config.security;

import com.yuriolivs.notification.shared.exceptions.http.HttpForbiddenException;
import com.yuriolivs.notification_service.config.properties.AuthProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private static AuthProperties authProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String apiKey = request.getHeader(authProperties.getHeader());

        if (authProperties.getKey().equals(apiKey)) {
            throw new HttpForbiddenException("Invalid API Key.");
        }

        filterChain.doFilter(request, response);
    }
}
