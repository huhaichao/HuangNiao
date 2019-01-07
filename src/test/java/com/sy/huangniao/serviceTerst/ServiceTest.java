package com.sy.huangniao.serviceTerst;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.HNApplication;
import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.enums.NotifyStatusEnum;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.common.exception.HNException;
import com.sy.huangniao.controller.context.HNContext;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.service.IRedisService;
import com.sy.huangniao.service.ITicketService;
import com.sy.huangniao.service.NotifyService;
import com.sy.huangniao.service.OtherPartyService;
import com.sy.huangniao.service.RobCheckService;
import com.sy.huangniao.service.customer.TicketCustomerService;
import com.sy.huangniao.service.impl.AbstractUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huchao on 2018/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HNApplication.class)
@Slf4j
public class ServiceTest {

    @Autowired
    TicketCustomerService ticketCustomerServiceImpl;

    @Autowired
    RobCheckService robCheckServiceImpl;

    @Autowired
    NotifyService notifyServiceImpl;

    @Autowired
    OtherPartyService otherPartyServiceImpl;

    @Autowired
    HNContext hnContext;

    @Autowired
    IRedisService redisServiceImpl;

    @Autowired
    ITicketService ticketServiceImpl;

    @Test
    public  void  returnTest(){
        log.info("......退款处理启动......");
        ticketCustomerServiceImpl.returnOrderHandle(null);
        log.info("......退款处理结束......");
    }


    @Test
    public  void  robCheckTest(){
        log.info("抢单状态监听启动.....");
        robCheckServiceImpl.robCheck();
        log.info("抢单状态监听结束.....");
    }

    @Test
    public void notifyTset() {
        log.info("系统通知启动.....");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum",0);
        jsonObject.put("pageSize",1000);
        jsonObject.put("notifyStatus", NotifyStatusEnum.WAIT_NOTIFY);
        List<Notify> list = notifyServiceImpl.selectList(jsonObject);
        for (Notify notify : list){
            try{
                if (StringUtils.isNotBlank(notify.getToNo()) && StringUtils.isNotBlank(notify.getContext())){
                    JSONObject json = new JSONObject();
                    json.put("phoneNo",notify.getToNo());
                    otherPartyServiceImpl.sendPhoneCode(json,notify.getContext(),false);
                    //修改notify表状态
                    Notify  notifyUpdate = new Notify();
                    notifyUpdate.setId(notify.getId());
                    notifyUpdate.setModifyDate(new Date());
                    notifyUpdate.setNotifyDate(new Date());
                    notifyUpdate.setNotifyStatus(NotifyStatusEnum.NOTIFY_SUCCESS.getStatus());
                    notifyServiceImpl.update(notifyUpdate);
                }
            }catch (Exception e){
                log.info("系统通知异常 e={} notify={}",notify,e.getMessage());
            }


        }
        log.info("系统通知结束.....");
    }

    @Test
    public void confirmTest(){
        try {
            AbstractUserinfoService abstractUserinfoService = hnContext.getAbstractUserinfoService("customer");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id","159");
            abstractUserinfoService.confirmeOrder(jsonObject);
        }catch (HNException e){
            log.info("requestBody={} confirmeOrder exception code={} msg={}",e.getCode(),e.getMsg());
        }catch (Exception e){
            log.info("requestBody={} confirmeOrder exception={}",e.getMessage());
        }
    }


    @Test
    public void redisTest(){
        log.info(" ----- ");
        redisServiceImpl.set("USERLOGINKEYXCX4","3FB4D89673");
        log.info("={}-----",redisServiceImpl.get("USERLOGINKEYXCX4",String.class));
    }

    @Test
    public  void  getTicketList(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("departureDate","2018-02-02");
            jsonObject.put("fromSiteDh","BOP");
            jsonObject.put("toSiteDh","ZZF");

            JSONArray list = ticketServiceImpl.getTicketInfoList(jsonObject);
            log.info(list.toString());
        } catch (Exception e) {
            log.info(" getTicketList ex={}", e.getMessage());

        }
    }
}
