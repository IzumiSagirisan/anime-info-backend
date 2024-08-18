package com.situ.anime.mapper;

import com.situ.anime.domain.entity.Anime;
import com.situ.anime.domain.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liangyunfei
 */
@Mapper
public interface AnimeMapper {
    Integer insert(Anime anime);

    List<Anime> selectAllRough();

    // 按年-季查询新番
    List<Anime> selectAllRoughByYearOrSeason();

    // 按照id查询单个新番信息
    Anime selectById(Integer id);

    // 更改新番信息
    Integer update(Anime anime);

    // 删除新番
    Integer delete(Integer id);
}
