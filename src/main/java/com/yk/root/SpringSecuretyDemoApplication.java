package com.yk.root;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yk.root.mapper")
public class SpringSecuretyDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecuretyDemoApplication.class, args);
	}
}