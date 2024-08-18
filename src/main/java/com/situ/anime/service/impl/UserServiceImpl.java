package com.situ.anime.service.impl;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.mapper.UserMapper;
import com.situ.anime.service.UserService;
import com.situ.anime.utils.MD5Util;
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

    /**
     * 实现用户注册操作
     */
    @Override
    public Integer addUser(UserVo user) throws Exception {
        System.out.println("我是service层的user" + user.toString());
        // 首先检查是否存在相同的用户名
        if (!ObjectUtils.isEmpty(userMapper.selectByUsername(user.getUsername()))) {
            // TODO: 前端需要做"即时"校验
            throw new Exception("用户名已被注册!");
        }

        // 检查两次密码是否一致
        if (!user.getPassword().equals(user.getRePassword())) {
            // TODO: 这里需要在前端也进行一次判断
            throw new Exception("两次输入密码不一致!");
        }

        // 检查手机号是否已经存在
        UserVo tempUser = new UserVo();
        tempUser.setPhone(user.getPhone());
        if (!ObjectUtils.isEmpty(userMapper.search(tempUser))) {
            throw new Exception("手机号已被注册!");
        }

        user.setPassword(MD5Util.getDBMD5(user.getPassword(), MD5Util.getSalt()));

        return userMapper.insert(user);
    }

    /**
     * 实现用户获取个人信息
     */
    @Override
    public User getUser(UserVo user) throws Exception {
        System.out.println("我是service层的getUser方法的UserVo" + user.toString());
        // 检查是否存在用户
        User userTemp = checkUser(user.getId());

        if (!MD5Util.getDBMD5(user.getPassword(), MD5Util.getSalt()).equals(userTemp.getPassword())) {
            throw new Exception("密码错误");
        }

        return userTemp;
    }

    /**
     * 实现修改用户信息
     */
    @Override
    public Integer editUser(UserVo user) throws Exception {
        return userMapper.update(user);
    }

    /**
     * 修改密码，service层需要做逻辑校验
     */
    @Override
    public Integer changePassword(UserVo newUser) throws Exception {
        // 数据库中已经有的数据
        User userTemp = checkUser(newUser.getId());

        // 旧密码验证
        String md5OldPassword = MD5Util.getDBMD5(newUser.getOldPassword(), MD5Util.getSalt());
        if (!md5OldPassword.equals(userTemp.getPassword())) {
            throw new Exception("旧密码不正确");
        }
        System.out.println(newUser);
        // 如果通过了旧密码验证，就更新密码
        newUser.setPassword(MD5Util.getDBMD5(newUser.getPassword(), MD5Util.getSalt()));

        return userMapper.update(newUser);
    }

    /**
     * 用户删除
     */
    @Override
    public Integer removeUser(UserVo user) throws Exception {
        User userTemp = checkUser(user.getId());

        // 密码验证
        String md5Password = MD5Util.getDBMD5(user.getPassword(), MD5Util.getSalt());
        if (!md5Password.equals(userTemp.getPassword())) {
            throw new Exception("旧密码不正确");
        }

        return userMapper.deleteUser(user.getId());
    }

    /**
     * 实现login
     */
    @Override
    public Integer login(UserVo user) {
        // 获取AuthenticationManager 进行用户认证

        // 如果认证没通过，给出对应的提示

        // 认证通过了，使用userId生成一个JWT

        // 把完整的用户信息存入
        return 0;
    }

    /**
     * 检查用户是否存在
     *
     * @param id 用户id
     * @return 用户实体类
     * @throws Exception 用户名不存在
     */
    public User checkUser(Integer id) throws Exception {
        User userTemp = userMapper.selectById(id);
        if (ObjectUtils.isEmpty(userTemp)) {
            throw new Exception("用户不存在");
        }
        return userTemp;
    }
}
