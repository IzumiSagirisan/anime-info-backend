package com.situ.anime.security.handler;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.security.domain.LoginUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author liangyunfei
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("我是principal:"+principal);
        LoginUser loginUser = (LoginUser) principal;
        User user = loginUser.getUser();
        System.out.println("我是User:"+user);
        response.getWriter().write("loginSuccess");


        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        System.out.println("authentication.getCredentials() = " + authentication.getCredentials());
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());

    }
}
