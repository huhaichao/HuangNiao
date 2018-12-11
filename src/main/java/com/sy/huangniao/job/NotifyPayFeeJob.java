package com.sy.huangniao.job;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.NotifyStatusEnum;
import com.sy.huangniao.common.enums.NotifyTypeEnum;
import com.sy.huangniao.common.enums.OrderStatusEnum;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.pojo.TicketOrder;
import com.sy.huangniao.service.NotifyService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
*  Created by huchao on 2018/12/11.
*/
@Component
@Slf4j
public class NotifyPayFeeJob {

    @Autowired
    AbstractUserinfoService ticketCustomerServiceImpl;

    @Autowired
    NotifyService notifyServiceImpl;

    @Value("${http.sms.payfee.content}")
    private String payFeeContent;

    @Scheduled(cron = "* 30 * * * ?")
    public void execute() {
        log.info("......通知付款启动......");
        JSONObject query = new JSONObject();
        query.put("pageNum", "0");
        query.put("pageSize", "1000");
        query.put("orderStatus", OrderStatusEnum.WAITPAY.getStatus());
        JSONObject jsonObject = ticketCustomerServiceImpl.getOrderList(query);
        if (jsonObject == null) {
            log.info("......通知付款结束 待付款记录为null......");
            return;
        }
        List<TicketOrder> list = jsonObject.getObject("datas", List.class);
        //通知用户付款
        for (TicketOrder ticketOrder : list) {
            String content = payFeeContent;
            Notify notify = new Notify();
            notify.setTitle(NotifyTypeEnum.PAYNOTIFY.getType());
            notify.setFromNo(ticketOrder.getOrderNo());
            notify.setToNo(ticketOrder.getPhoneNo());
            notify.setCreateDate(new Date());
            notify.setModifyDate(new Date());
            notify.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
            if (AppCodeEnum.XCX.getCode().equalsIgnoreCase(ticketOrder.getAppCode())) {
                content = content.replace("orderNo", ticketOrder.getOrderNo()).replace("fromSite",
                    ticketOrder.getFromSite()).replace("toSite", ticketOrder.getToSite())
                    .replaceAll("app", "牛小奔小程序");
            }
            notify.setContext(content);
            notify.setMsgType(NotifyTypeEnum.PAYNOTIFY.getType());
            notifyServiceImpl.save(notify);
            //通知超过3次人工借入逻辑



        }

        log.info("......通知付款结束......");
    }
}
