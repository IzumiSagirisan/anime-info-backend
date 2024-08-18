package com.situ.anime.security.handler;

import com.alibaba.fastjson.JSON;
import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.security.domain.LoginUser;
import com.situ.anime.security.utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liangyunfei
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        String token = JwtUtils.token(authentication);
        Map<String, Object> tokenInfo = new HashMap<>();

        System.out.println("我是LoginSuccessHandler里面的authentication: "+authentication.toString());
        // 需要返回一个用户角色，来决定跳转到什么页面
        // 从认证信息中获取用户信息
        System.out.println("我是principal: "+authentication.getPrincipal().toString());
        LoginUser user = (LoginUser) authentication.getPrincipal();
        String authorities = user.getAuthorities().toString();
        System.out.println("我是LoginUser里面的authorities!"+authorities);
        tokenInfo.put("role", authorities);
        // 传回用户信息
        tokenInfo.put("user", user);
        System.out.println("我是LoginUser里面的user!"+user.getUser().toString());

        tokenInfo.put("token", token);
        response.getWriter().write(JSON.toJSONString(tokenInfo));

        // 角色判断

    }
}