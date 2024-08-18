package com.situ.anime.security.custom;

import com.situ.anime.security.domain.LoginUser;
import com.situ.anime.security.utils.PasswordEncoderUtil;
import lombok.AllArgsConstructor;
import com.situ.anime.security.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 用来进行角色判断
 * 错了，不进行角色的授权检查
 * @author liangyunfei
 */
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoderUtil  passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws
            AuthenticationException {
        System.out.println("我是RoleAuthenticationProvider里面的authentication:"+authentication
        );
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        System.out.println(username+password);

        LoginUser user = (LoginUser) userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("验证失败，用户名或密码错误");
        }
        // 在这里添加权限控制吧
        System.out.println(user.getUser());
        if(user.getUser().getStatus() != 0){
            throw new BadCredentialsException("\uD83D\uDE22\t你被ban了,下次不要做坏事了哦");
        }

        return new UsernamePasswordAuthenticationToken(
                user,
                authentication.getCredentials(),
                user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
