package com.situ.anime.service;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.UserVo;

/**
 * @author liangyunfe
 */
public interface UserService {
    Integer addUser(UserVo user) throws Exception;
}
