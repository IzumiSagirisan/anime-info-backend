package com.situ.anime.mapper;

import com.situ.anime.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liangyunfei
 */
@Mapper
public interface UserMapper {
    Integer insert(User user);

    // 根据用户id进行查询
    User selectById(Integer id);

    // 多条件组合查询
    User search(User user);

    User selectByUsername(String username);
}
