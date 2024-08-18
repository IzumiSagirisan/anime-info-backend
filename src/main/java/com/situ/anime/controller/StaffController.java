package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.StaffService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangyunfei
 */
@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    /**
     * 根据id查询单个staff信息
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        try{
            return Result.success(staffService.searchById(id));
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
