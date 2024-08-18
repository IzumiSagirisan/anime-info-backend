package com.situ.anime.service.impl;

import com.situ.anime.domain.entity.Anime;
import com.situ.anime.mapper.AnimeMapper;
import com.situ.anime.service.AnimeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangyunfei
 */
@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    private final AnimeMapper animeMapper;

    @Override
    public Integer addAnime(Anime anime) throws Exception {

        return animeMapper.insert(anime);
    }

    @Override
    public List<Anime> roughSearchAll() throws Exception {
        return animeMapper.selectAllRough();
    }

    @Override
    public List<Anime> roughSearchYearOrSeason(Integer year, Integer season) {
        return animeMapper.selectAllRoughByYearOrSeason();
    }

    @Override
    public Anime searchById(Integer id) {
        return animeMapper.selectById(id);
    }

    @Override
    public Integer editAnime(Anime anime) throws Exception {
        return animeMapper.update(anime);
    }

    @Override
    public Integer removeAnime(Integer id) throws Exception {
        return animeMapper.delete(id);
    }
}
