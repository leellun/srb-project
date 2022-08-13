package com.newland.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.newland.srb.oss","com.newland.core"})
public class ServiceOssApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(ServiceOssApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
