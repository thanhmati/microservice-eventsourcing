package com.ltfullstack.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProfileserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileserviceApplication.class, args);
    }
}
