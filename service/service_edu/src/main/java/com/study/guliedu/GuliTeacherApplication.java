package com.study.guliedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan(basePackages = "com.study")
@ComponentScan(basePackages = "com.study")
public class GuliTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliTeacherApplication.class, args);
    }

}
