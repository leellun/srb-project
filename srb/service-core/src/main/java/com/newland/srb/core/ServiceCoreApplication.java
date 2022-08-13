package com.newland.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.newland.srb","com.newland.core"})
public class ServiceCoreApplication {

    public static void main(String[] args) {
        try{
        SpringApplication.run(ServiceCoreApplication.class, args);
        }catch(Throwable e) {
            e.printStackTrace();
        }
    }

}
