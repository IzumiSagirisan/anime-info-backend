package com.situ.anime.controller;

import com.situ.anime.domain.entity.Anime;
import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author liangyunfei
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;

    /**
     * 粗略查询，展示在首页的新番粗略列表
     */
    @RequestMapping("/rough/all")
    public Result searchAllRoughly(){
        try {
            System.out.println("已收到新番请求");
            return Result.success(animeService.roughSearchAll());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 按年-季查询新番, 展示在记录页面
     */
    @GetMapping("/{year}/{season}")
    public Result searchAllRoughlyByYearOrSeason(@PathVariable Integer year, @PathVariable Integer season){
        try {
            System.out.println(year+" "+season);
            return Result.success(animeService.roughSearchYearOrSeason(year, season));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 按照id查询新番信息
     */
    @GetMapping("/{id}")
    public Result searchById(@PathVariable Integer id){
        try {
            return Result.success(animeService.searchById(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }


}
