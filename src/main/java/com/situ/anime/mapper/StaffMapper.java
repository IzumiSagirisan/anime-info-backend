package com.situ.anime.mapper;

import com.situ.anime.domain.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liangyunfei
 */
@Mapper
public interface StaffMapper {
    Staff selectById(Integer id);
}
