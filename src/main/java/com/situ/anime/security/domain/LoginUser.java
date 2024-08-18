package com.situ.anime.security.domain;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author liangyunfei
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("\n我是LoginUser里面的user!"+user);
        return user.getUsername();
    }
}
