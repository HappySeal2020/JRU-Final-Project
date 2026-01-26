package com.javarush.zdanovskih;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.javarush.zdanovskih.constant.Const.WEB_MAP;


@Slf4j
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class Project5Application {
    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", WEB_MAP);
        SpringApplication.run(Project5Application.class, args);
        log.info("http://localhost:8080/");
    }
}
