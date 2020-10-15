package com.itheima.health.h5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeiXinMain {
    public static void main(String[] args) {
        SpringApplication.run(WeiXinMain.class,args);
        System.out.println(("---微信端启动----"));

    }
}
