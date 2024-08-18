package com.situ.anime.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.anime.security.exception.VerificationCodeException;
import com.situ.anime.security.handler.LoginFailureHandler;
import io.micrometer.common.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author liangyunfei
 */
@Slf4j
public class CaptchaFilter extends OncePerRequestFilter {
    // 获得登录失败处理器的类，如果验证码校验不通过，就直接调用登录失败处理器
    AuthenticationFailureHandler authenticationFailureHandler = new LoginFailureHandler();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 如果不是登录请求，就直接放行
        if (!("/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod()))) {
            filterChain.doFilter(request, response);
        } else {
            // 判断是登录请求，就进行验证码校验
            try {
                request = verificationCode(request);

                filterChain.doFilter(request, response);
            } catch (VerificationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public HttpServletRequest verificationCode(HttpServletRequest request) throws Exception {
        HttpSession httpSession = request.getSession();
        System.out.println(request.getAttribute("captcha"));

        // session中保存的是之前发送到前端的验证码, request中是目前登录的验证码
        // ! 这里的request必须读流 !
        String sessionCode = httpSession.getAttribute("captcha").toString();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("Parameter Name: " + paramName);
        }

        System.out.println("我是sessionCode:"+sessionCode);
        String requestBody = readLine(request);
        System.out.println(requestBody);

        String requestCode;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将 JSON 字符串转换为 Map
            @SuppressWarnings("rawtypes") Map map = objectMapper.readValue(requestBody, Map.class);
            requestCode = map.get("captcha").toString();
            request.setAttribute("username", map.get("username"));
            request.setAttribute("password", map.get("password"));
            request.setAttribute("captcha", requestCode);
            // 打印 Map
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        System.out.println("我是requestCode:"+requestCode);

        if (requestCode != null) {
            System.out.println("我是requestCode"+requestCode);
        }else{
            // 验证码为空,要抛出异常
            throw new VerificationCodeException("验证码为空");
        }

        // 判断session中的是不是空，或者验证码是否正确
        if (StringUtils.isEmpty(sessionCode) || !requestCode.equals(sessionCode)) {
            throw new VerificationCodeException("请重新请求验证码或重试");
        }

        // 我是用来清除每次cookie中的已有的验证码信息
        // cookies中有sessionID，session是保存在服务器的
        if (!StringUtils.isEmpty(requestCode)) {
            httpSession.removeAttribute("captcha");
        }
        return request;
    }

    public String readLine(HttpServletRequest request) throws ServletException {
        try (BufferedReader reader = request.getReader()) {
            String line;
            StringBuilder requestBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                requestBody.append(line).append("\n");
            }
            reader.close();
            return requestBody.toString();
        } catch (IOException e) {
            throw new ServletException("Error reading from reader", e);
        }
    }

}
