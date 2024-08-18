package com.situ.anime.controller;

import com.situ.anime.domain.entity.Anime;
import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 不仅完成对管理员的操作，也同时包含对其他数据库表的增删改操作
 * @author liangyunfei
 */
@RequestMapping("/manager")
@RequiredArgsConstructor
@RestController
public class ManagerController {
    private final AnimeService animeService;

    /**
     * 增加新番
     */
    @PostMapping
    public Result addAnime(@RequestBody Anime anime){
        try{
            return Result.success(animeService.addAnime(anime));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改新番信息，根据id
     */
    @PutMapping
    public Result editAnime(@RequestBody Anime anime){
        try {
            return Result.success(animeService.editAnime(anime));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
