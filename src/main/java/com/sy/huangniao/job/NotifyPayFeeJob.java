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
public class NotifyPayFeeJob {

    @Autowired
    AbstractUserinfoService ticketCustomerServiceImpl;

    @Autowired
    NotifyService notifyServiceImpl;

    @Autowired
    HNContext hnContext;

    @Value("${http.sms.payfee.content}")
    private String payFeeContent;

    @Value("${http.sms.payfee.count}")
    private int payFeeCount;

    @Scheduled(cron = "* */1 * * * ?")
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
            try {
                String content = payFeeContent;
                Notify queryNotify = new Notify();
                queryNotify.setFromNo(ticketOrder.getOrderNo());
                queryNotify = notifyServiceImpl.selectObject(queryNotify);
                if (queryNotify == null) {
                    Notify notify = new Notify();
                    notify.setTitle(NotifyTypeEnum.PAYFEE.getType());
                    notify.setFromNo(ticketOrder.getOrderNo());
                    notify.setToNo(ticketOrder.getPhoneNo());
                    notify.setCreateDate(new Date());
                    notify.setModifyDate(new Date());
                    notify.setNotifyCount(0);
                    notify.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
                    notify.setMsgType(NotifyTypeEnum.PAYFEE.getType());
                    if (AppCodeEnum.XCX.getCode().equalsIgnoreCase(ticketOrder.getAppCode())) {
                        content = content.replace("orderNo", ticketOrder.getOrderNo()).replace("fromSite",
                            ticketOrder.getFromSite()).replace("toSite", ticketOrder.getToSite())
                            .replaceAll("app", "牛小奔小程序");
                    }
                    notify.setContext(content);
                    notifyServiceImpl.save(notify);
                    continue;
                }
                int count = queryNotify.getNotifyCount();
                if (count > payFeeCount) {
                    log.info(" orderNo={} 通知支付次数count={} 超过payFeeCount ={}", ticketOrder.getOrderNo(), count,
                        payFeeCount);
                    continue;
                }
                if (count == payFeeCount) {
                    //通知超过3次人工借入逻辑
                    IDaoService<QustionOrder> iDaoService = hnContext.getDaoService(QustionOrder.class.getSimpleName());
                    QustionOrder qustionOrder = new QustionOrder();
                    qustionOrder.setOrderId(ticketOrder.getId());
                    qustionOrder.setOrderNo(ticketOrder.getOrderNo());
                    qustionOrder.setQustionType(QustionTypeEnum.PAYFEE3.getType());
                    qustionOrder.setStatus(QustionStatusEnum.WAITHANDLE.getStatus());
                    qustionOrder.setRemark(QustionTypeEnum.PAYFEE3.getDesc());
                    qustionOrder.setCreateDate(new Date());
                    qustionOrder.setModifyDate(new Date());
                    log.info("orderNo={} 超过{}次未支付进入人工处理流程", ticketOrder.getOrderNo(), count);
                    iDaoService.save(qustionOrder, SqlTypeEnum.DEAFULT);
                    //修改订单状态
                    TicketOrder ticketOrderUpdate = new TicketOrder();
                    ticketOrderUpdate.setId(ticketOrder.getId());
                    ticketOrderUpdate.setOrderStatus(OrderStatusEnum.WAITPAYHANDLE.getStatus());
                    ticketOrderUpdate.setModifyDate(new Date());
                    IDaoService<TicketOrder> ticketOrderIDaoService = hnContext.getDaoService(
                        TicketOrder.class.getSimpleName());
                    log.info("orderNo={} 修改订单状态={}......", ticketOrder.getOrderNo(), OrderStatusEnum.WAITPAYHANDLE);
                    ticketOrderIDaoService.updateObject(ticketOrderUpdate, SqlTypeEnum.DEAFULT);
                    continue;
                }
                Notify notifyUpdate = new Notify();
                notifyUpdate.setId(queryNotify.getId());
                notifyUpdate.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
                notifyUpdate.setModifyDate(new Date());
                notifyServiceImpl.update(notifyUpdate);
            } catch (Exception e) {
                log.info("orderNo={}通知订单支付服务费异常={}", ticketOrder.getOrderNo(), e.getMessage());
            }

        }
        log.info("......通知付款结束......");
    }
}
