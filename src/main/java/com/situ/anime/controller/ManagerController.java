package com.situ.anime.controller;

import com.situ.anime.domain.entity.Anime;
import com.situ.anime.domain.entity.Staff;
import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.AnimeService;
import com.situ.anime.service.StaffService;
import com.situ.anime.service.UserService;
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
    private final StaffService staffService;
    private final UserService userService;

    /**
     * 增加新番
     */
    @RequestMapping("/addAnime")
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
    @RequestMapping("/editAnime")
    public Result editAnime(@RequestBody Anime anime){
        try {
            return Result.success(animeService.editAnime(anime));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除新番信息，根据id
     */
    @RequestMapping("/removeAnime")
    public Result removeAnime(Integer id){
        try {
            return Result.success(animeService.removeAnime(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 增加STAFF信息
     */
    @RequestMapping("/addStaff")
    public Result addStaff(@RequestBody Staff staff){
        try {
            return Result.success(staffService.addStaff(staff));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }


    /**
     * 修改STAFF信息
     */
    @RequestMapping("/editStaff")
    public Result editStaff(@RequestBody Staff staff){
        try {
            return Result.success(staffService.editStaff(staff));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除STAFF信息
     */
    @RequestMapping("/removeStaff")
    public Result removeStaff(Integer id){
        try {
            return Result.success(staffService.removeStaff(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 找到全部用户信息
     */
    @RequestMapping("/searchAllUser")
    public Result searchAllUser(){
        try {
            System.out.println("已收到用户请求");
            return Result.success(userService.searchAll());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
