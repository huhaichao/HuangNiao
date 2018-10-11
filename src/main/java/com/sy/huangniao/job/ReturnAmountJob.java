package com.sy.huangniao.job;


import com.sy.huangniao.service.customer.TicketCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/10/10.
 *
 * 退款定时任务
 *
 */
@Component
@Slf4j
public class ReturnAmountJob  {

    @Autowired
    TicketCustomerService ticketCustomerServiceImpl;

    /**
     * 退款 1分钟执行一次任务
     */
    @Scheduled(cron = "* */1 * * * ?")
    public void execute() {
       log.info("......退款处理启动......");
       ticketCustomerServiceImpl.returnOrderHandle(null);
       log.info("......退款处理结束......");
    }


}
