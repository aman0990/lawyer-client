package com.lawyertalk.com.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;

    public JWTRequestFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
//    https://github.com/baezzys/spring-react-google-oauth2/blob/main/spring-oauth/src/main/java/com/google/oauth/config/WebConfig.java

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie authCookie = Arrays.stream(cookies)
                        .filter(cookie -> "AUTH-TOKEN".equals(cookie.getName()))
                        .findFirst()
                        .orElse(null);
                if (authCookie != null) {
                    Authentication authentication = jwtUtils.verifyAndGetAuthentication(authCookie.getValue());
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            // Log the exception or handle it as needed
            // e.g., log.error("Error processing JWT authentication", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
