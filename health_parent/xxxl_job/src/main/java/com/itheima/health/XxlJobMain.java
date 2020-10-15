package com.itheima.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XxlJobMain {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobMain.class,args);
        System.out.println(("----xxx-job任务开启-----"));
    }
}
