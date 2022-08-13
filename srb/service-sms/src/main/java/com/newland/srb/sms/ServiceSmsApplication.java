package com.newland.srb.sms;

import com.newland.srb.common.SrbCommonApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.newland.srb"})
@EnableFeignClients
public class ServiceSmsApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceSmsApplication.class, args);
    }

}
