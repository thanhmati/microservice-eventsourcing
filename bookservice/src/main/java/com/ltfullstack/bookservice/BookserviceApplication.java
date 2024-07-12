package com.ltfullstack.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.ltfullstack.bookservice","com.ltfullstack.commonservice"})
public class BookserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookserviceApplication.class, args);
	}

}
