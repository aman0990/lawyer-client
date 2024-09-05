package com.lawyertalk.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/oauth2/**", "/api/clients").permitAll()  // Allow OAuth2 endpoints
                                .anyRequest().authenticated()  // Protect other endpoints
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")  // Custom login page URL (optional)
                                .defaultSuccessUrl("/api/clients", true)  // Redirect after successful login
                                .failureUrl("/login?error")  // Redirect URL in case of login failure
                )
                .logout(logout ->
                        logout
                                .permitAll()  // Allow all users to log out
                                .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
                )
                .csrf(csrf -> csrf.disable());  // Disable CSRF for testing purposes; enable in production

        return http.build();
    }
}
