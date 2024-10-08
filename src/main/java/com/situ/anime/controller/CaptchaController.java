package com.situ.anime.controller;

import com.google.code.kaptcha.Producer;
import com.situ.anime.domain.vo.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author liangyunfei
 */
@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping
public class CaptchaController {

    private final Producer producer;

    @SneakyThrows
    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        System.out.println("已收到验证码请求");
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=captcha.jpg");
        String capText = producer.createText();
        log.info("验证码:{}",capText);

        request.getSession().setAttribute("captcha",capText);
        BufferedImage image = producer.createImage(capText);
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(image,"jpg",outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
