package com.economic.app.economic_app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${app.jwt.resetPassword.secret:economicAppResetPasswordSecretKey}")
    private String resetPasswordSecret;

    @Value("${app.jwt.resetPassword.expiration:900000}")
    private Long resetPasswordExpiration;

    public String getResetPasswordSecret() {
        return resetPasswordSecret;
    }

    public Long getResetPasswordExpiration() {
        return resetPasswordExpiration;
    }
} 