package com.situ.anime.service.impl;

import com.situ.anime.mapper.CommentMapper;
import com.situ.anime.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author liangyunfei
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    @Override
    public Integer getCommentCountByUserId(Integer id) {
        return commentMapper.countCommentByUserId(id);
    }

    @Override
    public Integer getCommentLikeCountByUserId(Integer id) {
        Integer count = commentMapper.countCommentLikeByUserId(id);
        if(count == null){
            return 0;
        }
        return count;
    }
}
