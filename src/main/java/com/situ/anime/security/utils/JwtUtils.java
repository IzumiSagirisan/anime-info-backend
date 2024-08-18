package com.situ.anime.security.utils;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.situ.anime.security.custom.JwtAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * @author liangyunfei
 */
public class JwtUtils {
    private static final String SECRET = "LYF";

    /**
     * 生成JWT token
     */
    public static String token(Authentication authentication){
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis()+1000L*60*60*24*30))
                .withAudience(JSON.toJSONString(authentication))
                .sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * 验证token合法性
     */
    public static void tokenVerify(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        // 若没有报错，则证明验证成功
        System.out.println("我正在进行JWT的验证");
        System.out.println(token);
        jwtVerifier.verify(token);
        System.out.println("我已经验证成功了");

        JWT.decode(token).getExpiresAt();
        String json = JWT.decode(token).getAudience().getFirst();

        JwtAuthentication jwtAuthentication = JSON.parseObject(json, JwtAuthentication.class);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
    }
}
