package com.situ.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangyunfei
 */
@SpringBootApplication
public class AnimeBackendApplication {

    public static void main(String[] args) {
        Object run = SpringApplication.run(AnimeBackendApplication.class, args);
        // Default
        // System.out.println("这里只是用于查询springSecurity的过滤器链");
    }

}
