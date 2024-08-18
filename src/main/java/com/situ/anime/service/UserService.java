package com.situ.anime.service;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;

/**
 * @author liangyunfe
 */
public interface UserService {
    Integer addUser(UserVo user) throws Exception;

    User getUser(UserVo user) throws Exception;

    Integer editUser(UserVo user) throws Exception;

    Integer changePassword(UserVo user) throws Exception;

    Integer removeUser(UserVo user) throws Exception;

    Integer login(UserVo user);
}
