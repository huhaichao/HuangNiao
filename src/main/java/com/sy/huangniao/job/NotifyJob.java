package com.sy.huangniao.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/10/10.
 *
 * 通知任务
 *
 */
//@Component
public class NotifyJob {

    /**
     * 通知 1秒执行一次任务
     */
    @Scheduled(cron = "* 1 * * * ?")
    public void execute() {



    }
}
