package com.sy.huangniao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by huchao on 2018/9/12.
 */
@SpringBootApplication(scanBasePackages = {"com.sy.huangniao"})
//@EnableScheduling
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
