package com.situ.anime.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author liangyunfei
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String message) {
        super(message);
    }
}
