package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/12/12.
 */
public enum QustionTypeEnum {

    PAYFEE3("pay_fee_3","用户短信三次通知未支付手续费");

    private  String type;

    private  String desc;


    QustionTypeEnum (String type,String desc){
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
