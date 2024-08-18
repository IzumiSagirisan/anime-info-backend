package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.service.CommentService;
import com.situ.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author liangyunfei
 * 使用路径参数的时候，要加上@PathVariable注解
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CommentService commentService;

    /**
     * 实现用户注册
     */
    @RequestMapping("/add")
    public Result register(@RequestBody UserVo user) {
        System.out.println("我是Controller层的user" + user);
        try {

            return Result.success(userService.addUser(user));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 实现用户修改密码
     */
    @RequestMapping("/changePassword")
    public Result changePassword(@RequestBody UserVo userVo){
        if(userVo.getPassword().length()<3 || userVo.getPassword().length()>12){
            return Result.error("新密码长度不合适");
        }
        try{
            return Result.success(userService.changePassword(userVo));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 实现用户注销功能
     */
    @DeleteMapping("{id}")
    public Result removeUser(@RequestBody UserVo userVo){
        try{
            return Result.success(userService.removeUser(userVo));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 实现修改用户信息
     */
    @PutMapping
    public  Result editUser(@RequestBody UserVo userVo){
        try{
            return Result.success(userService.editUser(userVo));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询自己的评论数量
     */
    @GetMapping("/commentCount/{id}")
    public Result getCommentCount(@PathVariable Integer id){
        try{
            return Result.success(commentService.getCommentCountByUserId(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查看自己的评论like数量
     */
    @GetMapping("/commentLikeCount/{id}")
    public Result getCommentLikeCount(@PathVariable Integer id){
        try{
            return Result.success(commentService.getCommentLikeCountByUserId(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

}
