package com.wsd.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.wsd.admin.dao.**.*")
public class WsdAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsdAdminApplication.class, args);
    }
}
