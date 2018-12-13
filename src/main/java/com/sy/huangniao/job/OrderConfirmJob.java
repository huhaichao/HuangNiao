package com.sy.huangniao.job;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.NotifyStatusEnum;
import com.sy.huangniao.common.enums.NotifyTypeEnum;
import com.sy.huangniao.common.enums.OrderStatusEnum;
import com.sy.huangniao.common.enums.QustionStatusEnum;
import com.sy.huangniao.common.enums.QustionTypeEnum;
import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.pojo.QustionOrder;
import com.sy.huangniao.pojo.TicketOrder;
import com.sy.huangniao.service.IDaoService;
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
public class OrderConfirmJob {

    @Autowired
    AbstractUserinfoService ticketCustomerServiceImpl;


    @Scheduled(cron = "0 0 23 * * ?")
    public void execute() {
        log.info("......订单确认启动......");
        JSONObject query = new JSONObject();
        query.put("pageNum", "0");
        query.put("pageSize", "1000");
        query.put("orderStatus", OrderStatusEnum.TICKET_SUCCESS.getStatus());
        JSONObject jsonObject = ticketCustomerServiceImpl.getOrderList(query);
        if (jsonObject == null) {
            log.info("......订单确认启动结束 待确认记录为null......");
            return;
        }
        List<TicketOrder> list = jsonObject.getObject("datas", List.class);
        if(list == null || list.size()==0){
            log.info("......订单确认结束 待确认记录为0......");
            return;
        }
        //通知用户付款
        for (TicketOrder ticketOrder : list) {
            try {
                JSONObject data = new JSONObject();
                data.put("id",ticketOrder.getId());
                data.put("userId",ticketOrder.getUserId());
                jsonObject.put("appCode",ticketOrder.getAppCode());
                ticketCustomerServiceImpl.confirmeOrder(data);
            } catch (Exception e) {
                log.info("orderNo={} 订单确认异常={}", ticketOrder.getOrderNo(), e.getMessage());
            }
        }
        log.info("......订单确认处理结束size={}......",list.size());
    }
}
