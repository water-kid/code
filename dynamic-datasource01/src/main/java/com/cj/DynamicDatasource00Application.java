package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cj")  // 为什么加了@Mapper 还要加@MapperScan
public class DynamicDatasource00Application {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDatasource00Application.class, args);
    }

}
