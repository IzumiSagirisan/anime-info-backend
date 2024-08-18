package com.situ.anime.security.config;

import com.situ.anime.security.filter.CaptchaFilter;
import com.situ.anime.security.filter.LoginFilter;
import com.situ.anime.security.handler.LoginFailureHandler;
import com.situ.anime.security.handler.LoginSuccessHandler;
import com.situ.anime.security.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 在security6中，已经没有WebSecurityConfigurerAdapter了
 * 该用的方法是注册一个SecurityFilterChain的bean
 *
 * @author liangyunfei
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 定义密码编码方案
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoderUtil();
    }

    /**
     * authorizeHttpRequests: 针对http请求进行授权的相关配置
     * login: 登录接口需要匿名访问
     * permitAll: 具有所有权限，也就是匿名可以访问
     * anyRequest: 任何请求 所有请求
     * authenticated: 认证【登录】
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // login登录页面需要匿名访问
        http.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                        // 匿名就可以访问
                        .requestMatchers("/login", "/user/add", "/captcha.jpg").permitAll()
                        // 必须有manager角色才能访问
                        .requestMatchers("/manager/*").hasRole("manager")
                        // 必须有manager或者user角色才能访问
                        .requestMatchers("/comment/*").hasAnyRole("manager", "user")

                        .anyRequest().authenticated()

        );
        /*
         * formLogin: 表单登录
         * http: 后面可以一直. 但是内容太多之后难以阅读
         * loginPage: 登录页面， 前后端分离时不需要
         * loginProcessUrl: 登录接口， 过滤器
         * defaultSuccessUrl: 登陆成功之后访问的页面
         * successHandler: 登陆成功处理器
         */
//        http.formLogin(formLogin ->
//                formLogin
//                        .loginProcessingUrl("/login").permitAll()
//                        .successHandler(new LoginSuccessHandler())
//                        .failureHandler(new LoginFailureHandler())
//        );

        // 配置自定义登录过滤器
        http.addFilterBefore(new CaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        // 把跨域漏洞防御关闭
        // csrf.disable == Customizer.withDefaults()
        http.csrf(AbstractHttpConfigurer::disable);

        // 关闭跨域拦截
        http.cors(Customizer.withDefaults());

//        // 退出
//        http.logout(logout->logout.invalidateHttpSession(true));

        return http.build();
    }

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return loginFilter;
    }
}
