package com.yuzb.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class VerifyCodeController {
    @Autowired
    Producer producer;

    /**
    * @Description: 登录验证码
    * @Param:  resp
    * @return:
    * @Date: 2021/5/23
    */
    @GetMapping("/vc.jpg")
    public void getVerifyCode(HttpServletResponse resp, HttpServletRequest request) throws IOException {
        resp.setContentType("image/jpeg");
        String text = producer.createText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        BufferedImage image = producer.createImage(text);
        try(ServletOutputStream out = resp.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        }
    }
}