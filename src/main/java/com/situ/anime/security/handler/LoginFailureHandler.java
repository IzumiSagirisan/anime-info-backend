package com.situ.anime.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liangyunfei
 */
@SuppressWarnings("ALL")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        System.out.println("我是异常信息:"+exception.getMessage());
        data.put("status", "error");
        data.put("message", exception.getMessage());

        System.out.println("登录失败");
        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
        exception.printStackTrace();

        // 将自定义数据写入响应体

    }
}
