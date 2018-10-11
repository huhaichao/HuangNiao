package com.sy.huangniao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by huchao on 2018/9/12.
 */
@SpringBootApplication(scanBasePackages = {"com.sy.huangniao"})
@EnableTransactionManagement
@PropertySources({
        @PropertySource(value="classpath:params.properties")
 })
@EnableScheduling
public class HNApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HNApplication.class, args);
    }
}
