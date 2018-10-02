package com.sy.huangniao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by huchao on 2018/9/12.
 */
@SpringBootApplication(scanBasePackages = {"com.sy.huangniao"})
@EnableTransactionManagement
//@EnableScheduling
public class HNApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HNApplication.class, args);
    }
}