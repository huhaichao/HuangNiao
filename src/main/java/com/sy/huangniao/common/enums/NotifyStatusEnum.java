package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/23.
 */
public enum NotifyStatusEnum {


    WAIT_NOTIFY("wait_notify","等待通知"),
    NOTIFY_SUCCESS("notify_success","通知成功"),
    NOTIFY_FAIL("notify_fail","通知失败"),
    NPTIFY_CANCEL("notify_cancel","通知取消");


    private  String status;

    private  String desc;


    NotifyStatusEnum (String status,String desc){
        this.status=status;
        this.desc=desc;
    }


    public String getStatus(){
        return  this.status;
    }

    public String getDesc(){
        return  this.desc;
    }
}
