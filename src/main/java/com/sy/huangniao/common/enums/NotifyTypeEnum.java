package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/11/18.
 */
public enum  NotifyTypeEnum {

    PAYNOTIFY("pay_notify","通知用户付款");

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
