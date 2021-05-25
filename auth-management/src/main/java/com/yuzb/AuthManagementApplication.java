package com.yuzb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yuzb.mapper")
@SpringBootApplication
public class AuthManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthManagementApplication.class, args);
    }

}
