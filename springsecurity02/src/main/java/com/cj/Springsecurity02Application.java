package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cj.mapper")
public class Springsecurity02Application {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurity02Application.class, args);
    }

}
