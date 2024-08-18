package com.situ.anime.service;

import com.situ.anime.domain.entity.Anime;

import java.util.List;

/**
 * @author liangyunfei
 */
public interface AnimeService {
    Integer addAnime(Anime anime) throws Exception;

    List<Anime> roughSearchAll() throws Exception;

    // 按年-季查询新番
    List<Anime> roughSearchYearOrSeason(Integer year, Integer season);

    // 按照ID查询新番信息
    Anime searchById(Integer id);

    Integer editAnime(Anime anime) throws Exception;
}
