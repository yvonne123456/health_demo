package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class,args);
        System.out.println("--服务消费者启动");
    }
}
