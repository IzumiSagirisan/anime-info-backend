package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author liangyunfei
 */
@RestController
@RequestMapping
public class TestController {
    /**
     * 测试用户权限
     */
    @RequestMapping("/test")
    public Result test(){
        System.out.println("test");
        Random random = new Random();
        System.out.println("已经来到test了");

        return Result.success(random.nextDouble());
    }
}
