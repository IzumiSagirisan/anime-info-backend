package com.situ.anime.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author liangyunfei
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException() {
        super("验证码错误");
    }
}
