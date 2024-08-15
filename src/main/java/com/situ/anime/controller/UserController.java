package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import com.situ.anime.domain.vo.UserVo;
import com.situ.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangyunfei
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @RequestMapping("/add")
    public Result add(@RequestBody UserVo user) {
        System.out.println(user);
        try {

            return Result.success(userService.addUser(user));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
