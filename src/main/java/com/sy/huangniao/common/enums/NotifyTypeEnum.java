package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/11/18.
 */
public enum  NotifyTypeEnum {

    PAYNOTIFY("pay_notify","通知用户去12306付款"),
    PAYFEE("pay_fee","通知用户支付平台服务费");

    private  String type;

    private  String desc;


    NotifyTypeEnum (String type,String desc){
        this.type=type;
        this.desc=desc;
    }


    public String getType(){
        return  this.type;
    }

    public String getDesc(){
        return  this.desc;
    }
}
