package com.sy.huangniao.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.NotifyStatusEnum;
import com.sy.huangniao.common.enums.NotifyTypeEnum;
import com.sy.huangniao.common.enums.OrderStatusEnum;
import com.sy.huangniao.common.enums.SqlTypeEnum;
import com.sy.huangniao.common.enums.UserRoleEnum;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.pojo.RobOrder;
import com.sy.huangniao.pojo.TicketOrder;
import com.sy.huangniao.service.IDaoService;
import com.sy.huangniao.service.NotifyService;
import com.sy.huangniao.service.RobCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by huchao on 2018/11/18.
 */
@Service
@Slf4j
public class RobCheckServiceImpl implements RobCheckService {

    @Autowired
    HNContext hnContext;

    @Autowired
    NotifyService notifyServiceImpl;

    @Value("${http.sms.payOrder.content}")
    private String payOrderContent;

    @Override
    public void robCheck() {

        AbstractUserinfoService abstractUserinfoService =hnContext.getAbstractUserinfoService(UserRoleEnum.BUSINESS.getRole());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum",0);
        jsonObject.put("pageSize",1000);
        jsonObject.put("robStatus","rob_check");
        JSONObject jsonreslut = abstractUserinfoService.getOrderList(jsonObject);
        JSONArray jsonArray = jsonreslut.getJSONArray("datas");
        if (jsonArray!=null  && jsonArray.size()>0){
            List<RobOrder> list =jsonArray.toJavaList(RobOrder.class);
            for (RobOrder robOrder : list){
                try {
                    String content = payOrderContent;
                    content =content.replaceAll("app","牛小奔");
                    TicketOrder ticketOrder = robOrder.getTicketOrder();
                    Notify notify = new Notify();
                    notify.setTitle(NotifyTypeEnum.PAYNOTIFY.getType());
                    notify.setFromNo("sys");
                    notify.setToNo(ticketOrder.getPhoneNo());
                    notify.setCreateDate(new Date());
                    notify.setModifyDate(new Date());
                    notify.setNotifyCount(0);
                    notify.setNotifyStatus(NotifyStatusEnum.WAIT_NOTIFY.getStatus());
                    notify.setContext(content+robOrder.getRobContext());
                    notify.setMsgType(NotifyTypeEnum.PAYNOTIFY.getType());
                    notifyServiceImpl.save(notify);
                    //修改ticketOrder订单状态
                    TicketOrder ticket = new TicketOrder();
                    ticket.setId(ticketOrder.getId());
                    ticket.setOrderStatus(OrderStatusEnum.WAITPAY.getStatus());
                    ticket.setModifyDate(new Date());
                    IDaoService<TicketOrder> ticketOrderIDaoService =hnContext.getDaoService(TicketOrder.class.getSimpleName());
                    ticketOrderIDaoService.updateObject(ticket,SqlTypeEnum.DEAFULT);
                    //修改roborder状态
                    RobOrder rob = new RobOrder();
                    rob.setId(robOrder.getId());
                    rob.setRobStatus(OrderStatusEnum.USER_PAY.getStatus());
                    rob.setModifyDate(new Date());
                    IDaoService<RobOrder> robOrderIDaoService =hnContext.getDaoService(RobOrder.class.getSimpleName());
                    robOrderIDaoService.updateObject(rob, SqlTypeEnum.DEAFULT);

                }catch (Exception e){
                    log.info("添加抢单信息失败 商户={} orderNo={} e={}",robOrder.getUserId(),robOrder.getOrderId(),e.getMessage());
                }
            }
        }

    }
}
