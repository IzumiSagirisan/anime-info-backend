package com.situ.anime.service;

import com.situ.anime.domain.entity.User;
import com.situ.anime.domain.vo.Result;
import com.situ.anime.domain.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<User> searchAll();

    List<User> searchManagerAndUser();

    Integer editAvatar(String fileAddress, Integer id) throws Exception;

    String searchAvatar(Integer id);

}
