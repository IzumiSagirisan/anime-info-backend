package com.situ.anime.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

/**
 * 这个过滤器取代了UsernamePasswordAuthenticationFilter的位置
 * 用处是进行用户权限的验证
 * 需要注意的是，在前后端完全分离的项目中，获取信息是从token中获取的。
 * 而在这个项目中，我们完全可以从session中获取相关信息。
 * 现在这里就是需要修改的地方， 把从request中获取的信息改成从session中获取
 * 不用了，最新操作，判断是不是login请求，如果不是直接放行
 *
 * @author liangyunfei
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        // 判断是不是POST请求
        //noinspection AliEqualsAvoidNull
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 判断是不是登录
        if (!("/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod()))) {
            // 存入验证信息,直接从session拿
            UsernamePasswordAuthenticationToken authRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            request.getSession().getAttribute("username"),
                            request.getSession().getAttribute("password"));

            // 直接return
            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            // 存入验证信息
            UsernamePasswordAuthenticationToken authRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            request.getAttribute("username"),
                            request.getAttribute("password"));

            return this.getAuthenticationManager().authenticate(authRequest);
        }


    }
}
