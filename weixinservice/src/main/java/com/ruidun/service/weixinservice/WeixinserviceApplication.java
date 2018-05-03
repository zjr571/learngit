package com.ruidun.service.weixinservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com/ruidun/service/weixinservice/mapper")
@SpringBootApplication
public class WeixinserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(WeixinserviceApplication.class, args);
	}
}
