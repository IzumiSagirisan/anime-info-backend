package com.situ.anime.service;

/**
 * @author liangyunfei
 */
public interface CommentService {
    Integer getCommentCountByUserId(Integer id);

    Integer getCommentLikeCountByUserId(Integer id);
}
