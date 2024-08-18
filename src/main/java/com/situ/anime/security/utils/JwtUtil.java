package com.situ.anime.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 * @author liangyunfei
 */
@SuppressWarnings("ALL")
public class JwtUtil {
    // 设置有效期
    public static final Long JWT_TTL = 60*60*1000L;
    // 设置秘钥明文
    public static final String JWT_KEY = "liangyunfei";

    // 获取UUID
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT
     * @param subject token中的数据，JSON格式
     * @return JWT
     */
    public static String createJwt(String subject){
        return creatJWT(subject, null);
    }


    public static String creatJWT(String subject, Long ttlMillis){
        return createJwt(subject, ttlMillis, getUUID());
    }

    public static String createJwt(String subject, Long ttlMillis, String id){
        return getJwtBuilder(subject, ttlMillis, id);
    }

    private static String getJwtBuilder(String subject, Long ttlMillis, String uuid){
        SecretKey secretKey = generalKey();
        // 如果没有设置过期时间，就设置成默认的时间
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("liangyunfei")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TTL))
                .compact();
    }

    /**
     * 生成加密后的秘钥
     */
    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
    }

    /**
     * 解析jwt
     */
    public static Claims parseJwt(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}