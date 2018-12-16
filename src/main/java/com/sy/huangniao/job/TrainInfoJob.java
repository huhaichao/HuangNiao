package com.sy.huangniao.job;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.enums.OrderStatusEnum;
import com.sy.huangniao.pojo.TicketOrder;
import com.sy.huangniao.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/12/15.
 */
@Slf4j
@Component
public class TrainInfoJob {

    @Autowired
    ITicketService ticketServiceImpl;

    @Scheduled(cron = "59 59 23 * * ?")
    public void execute() {
        log.info("......站点信息init启动......");
        ticketServiceImpl.initSite();
        log.info("......站点信息init结束......");
    }
}
