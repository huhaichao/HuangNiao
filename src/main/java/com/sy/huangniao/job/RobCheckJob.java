package com.sy.huangniao.job;

import com.sy.huangniao.service.RobCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/11/18.
 */
@Component
@Slf4j
public class RobCheckJob {

   @Autowired
   RobCheckService robCheckServiceImpl;

    /**
     * 通知 1秒执行一次任务
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void execute() {
      log.info("抢单状态监听启动.....");
        robCheckServiceImpl.robCheck();
      log.info("抢单状态监听结束.....");
    }
}
