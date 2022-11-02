package com.five.train_o_gram.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JtwAuthenticationException extends AuthenticationException {
    public JtwAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JtwAuthenticationException(String msg) {
        super(msg);
    }
}