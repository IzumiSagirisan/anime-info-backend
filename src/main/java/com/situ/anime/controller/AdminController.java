package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangyunfie
 */
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final UserService userService;

    @GetMapping("/searchAll")
    public Result searchManagerAndUser(){
        try{
            return Result.success(userService.searchManagerAndUser());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
