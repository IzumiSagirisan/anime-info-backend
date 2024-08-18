package com.situ.anime.controller;

import com.situ.anime.domain.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangyunfei
 */
//@RestController
//@RequestMapping("/login")
//@RequiredArgsConstructor
//public class LoginController {
//
//    public Authentication login(@RequestBody UserVo userVo){
//        System.out.println(userVo);
//        UsernamePasswordAuthenticationToken authRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(
//                        userVo.getUsername(),
//                        userVo.getPassword());
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
//
//    // 想自己写一个判断，但是怎么处理这个authenticationManager呢
//    // 这是个UsernamePasswordAuthenticationFilter里面继承父类的属性
//    private AuthenticationManager getAuthenticationManager() {
//        return this.authenticationManager;
//    }
//}
