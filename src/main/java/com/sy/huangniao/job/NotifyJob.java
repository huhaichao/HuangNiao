package com.sy.huangniao.job;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.common.Util.StringUtils;
import com.sy.huangniao.common.enums.NotifyStatusEnum;
import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.service.NotifyService;
import com.sy.huangniao.service.OtherPartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/10/10.
 *
 * 通知任务
 *
 */
@Component
@Slf4j
public class NotifyJob {

    @Autowired
    NotifyService notifyServiceImpl;

    @Autowired
    OtherPartyService otherPartyServiceImpl;
    /**
     * 通知 1秒执行一次任务
     */
    @Scheduled(cron = "* 1 * * * ?")
    public void execute() {
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
}
