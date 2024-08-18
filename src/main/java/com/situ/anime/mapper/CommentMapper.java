package com.situ.anime.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author liangyunfei
 */
@Mapper
public interface CommentMapper {
    Integer countCommentByUserId(Integer userId);

    Integer countCommentLikeByUserId(Integer id);
}
