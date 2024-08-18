package com.situ.anime.security.filter;

import com.situ.anime.security.exception.VerificationCodeException;
import com.situ.anime.security.handler.LoginFailureHandler;
import io.micrometer.common.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author liangyunfei
 */
@Slf4j
public class CaptchaFilter extends OncePerRequestFilter {
    AuthenticationFailureHandler authenticationFailureHandler = new LoginFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (!("/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod()))) {
            filterChain.doFilter(request, response);
        } else {
            try {
                verificationCode(request, response);
                filterChain.doFilter(request, response);
            } catch (VerificationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws VerificationCodeException {
        String requestCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("captcha");
        if (!StringUtils.isEmpty(sessionCode)) {
            session.removeAttribute("captcha");
        }

        if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(sessionCode) || !requestCode.equals(sessionCode)) {
            throw new VerificationCodeException();
        }
    }
}
