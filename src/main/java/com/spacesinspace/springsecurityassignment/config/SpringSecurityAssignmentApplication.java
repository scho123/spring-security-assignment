package com.spacesinspace.springsecurityassignment.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spacesinspace.springsecurityassignment")
@MapperScan(basePackages = "com.spacesinspace.springsecurityassignment", annotationClass = Mapper.class)
public class SpringSecurityAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAssignmentApplication.class, args);
    }

}
