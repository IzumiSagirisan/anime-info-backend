package com.situ.anime.security.service.impl;

import com.situ.anime.domain.entity.User;
import com.situ.anime.mapper.UserMapper;
import com.situ.anime.security.domain.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author liangyunfei
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1- 查询用户信息
        User user = userMapper.selectByUsername(username);

        if(Objects.isNull(user)){
            // 没有查询到用户
            System.out.println("用户名未找到");
            throw new UsernameNotFoundException("用户名未找到!");
        }

        // 查询对应的权限信息: 不做了，没时间
        // 把数据封装成UserDetails再返回
        return new LoginUser(user);
    }
}
