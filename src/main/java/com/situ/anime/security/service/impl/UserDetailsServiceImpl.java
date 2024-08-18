package com.situ.anime.security.service.impl;

import com.situ.anime.domain.entity.User;
import com.situ.anime.mapper.UserMapper;
import com.situ.anime.security.domain.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
            // todo: 直接统一到User表中
            throw new UsernameNotFoundException("用户名未找到!");
        }

//        // 1.5- 查询用户有没有被禁用
//        if (user.getStatus() == 1){
//            throw new UsernameNotFoundException("用户被禁用!");
//        }

        // 2- 查询用户角色
        Set<GrantedAuthority> authorities = new HashSet<>(Set.of());
        Integer roleGroup = userMapper.selectRole(user.getId());
        String role = null;
        switch (roleGroup){
            case 0:{
                role = "ROLE_USER";
                break;
            }
            case 1:{
                role = "ROLE_SUPERUSER";
                break;
            }
            case 2:{
                role = "ROLE_MANAGER";
                break;
            }
            case 3:{
                role = "ROLE_ADMIN";
                break;
            }
            default:{
                System.out.println("角色信息出错了!");
                break;
            }
        }

        // 把String类型的role转换成GrantedAuthority类型
        System.out.println("我是String的role:"+role);
        authorities.add(new SimpleGrantedAuthority(role));
        System.out.println("我是Set的authorities"+authorities);

        // 把数据封装成UserDetails再返回
        return new LoginUser(user, authorities);
    }
}
