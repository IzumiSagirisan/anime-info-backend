package com.situ.anime.service.impl;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.mapper.UserMapper;
import com.situ.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author liangyunfei
 * 数据有效性的验证，交给Service层
 * 数据格式的验证，交给Controller层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Integer addUser(UserVo user) throws Exception {
        System.out.println("我是service层的user"+user.toString());
        // 首先检查是否存在相同的用户名
        if(!ObjectUtils.isEmpty(userMapper.selectByUsername(user.getUsername()))){
            // TODO: 前端同样需要进行用户名
            throw new Exception("用户名已被注册!");
        }

        // 检查两次密码是否一致
        if(!user.getPassword().equals(user.getRePassword())){
            // TODO: 这里需要在前端也进行一次判断
            throw new Exception("两次输入密码不一致!");
        }

        // 检查手机号是否已经存在
        UserVo tempUser = new UserVo();
        tempUser.setPhone(user.getPhone());
        if(!ObjectUtils.isEmpty(userMapper.search(tempUser))){
            // TODO: 前端也需要做及时校验
            throw new Exception("手机号已被注册!");
        }

        return userMapper.insert(user);
    }
}
