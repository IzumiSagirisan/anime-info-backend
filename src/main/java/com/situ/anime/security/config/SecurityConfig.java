package com.situ.anime.security.config;

import com.situ.anime.security.custom.CustomAuthenticationProvider;
import com.situ.anime.security.filter.CaptchaFilter;
import com.situ.anime.security.filter.JwtAuthenticationTokenFilter;
import com.situ.anime.security.filter.LoginFilter;
import com.situ.anime.security.filter.StatusFilter;
import com.situ.anime.security.handler.LoginFailureHandler;
import com.situ.anime.security.handler.LoginSuccessHandler;
import com.situ.anime.security.handler.MyAccessDeniedHandler;
import com.situ.anime.security.handler.MyLogoutSuccessfulHandler;
import com.situ.anime.security.service.impl.UserDetailsServiceImpl;
import com.situ.anime.security.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * 在security6中，已经没有WebSecurityConfigurerAdapter了
 * 该用的方法是注册一个SecurityFilterChain的bean
 *
 * @author liangyunfei
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoderUtil passwordEncoder;

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    /**
     * 定义密码编码方案
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderUtil();
    }

    // 定义MyAccessDeniedHandler的Bean
    @Bean
    public MyAccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(CustomAuthenticationProvider customAuthenticationProvider) {
        return new ProviderManager(customAuthenticationProvider);
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
                        // 测试类也需要有manager的角色控制
                        .requestMatchers("/test").hasRole("USER")
                        // 必须有manager角色才能访问
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        // 需要USER才能访问
                        .requestMatchers("/user/**").hasAnyRole("USER", "MANAGER", "ADMIN", "SUPERUSER")
                        // 必须有manager或者user角色才能访问
                        .requestMatchers("/comment/**", "/file/**").hasAnyRole("MANAGER", "SUPERUSER", "USER", "ADMIN")
                        .anyRequest().authenticated()
        );

//        http.authenticationManager(authenticationManagerBean());

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
        http.addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new CaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(new StatusFilter(), LoginFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);


        // 异常处理
        http.exceptionHandling(ex -> ex.
                accessDeniedHandler(myAccessDeniedHandler()));

//        // session失效, 直接换jwt了
//        http.sessionManagement(sessionManagement -> sessionManagement
//                .invalidSessionStrategy(new MyInvalidSession())
//                .invalidSessionUrl("/login")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//        );
        // 把跨域漏洞防御关闭
        http.csrf(AbstractHttpConfigurer::disable);

        // 关闭跨域拦截
        http.cors(Customizer.withDefaults());

        // 退出
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new MyLogoutSuccessfulHandler()));

//        // 记住我
//        // Key是cookie加密的秘钥
//        http.rememberMe(e->e
//                .rememberMeParameter("rememberMe")
//                .rememberMeCookieName("rememberMe")
//                .key("myKey"));

        return http.build();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return loginFilter;
    }

}
