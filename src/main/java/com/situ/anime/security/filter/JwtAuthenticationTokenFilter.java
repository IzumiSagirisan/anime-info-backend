package com.situ.anime.security.filter;

import com.situ.anime.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author liangyunfei
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 获取JWT
        String token = request.getHeader("token");

        if(token!=null && !"undefined".equals(token)){
            try{
                JwtUtils.tokenVerify(token);
                System.out.println("token验证通过");
            }catch (Exception e){
                System.out.println("token验证失败");
                throw new ServletException("token验证失败");
            }
        }
        filterChain.doFilter(request, response);
    }
}
