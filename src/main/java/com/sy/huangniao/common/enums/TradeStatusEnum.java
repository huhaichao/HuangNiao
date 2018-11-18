package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/26.
 */
public enum  TradeStatusEnum {

    SUCCESS("success","成功"),
    TRADE_AUDITING("trade_auditing","出款审核中"),
    TRADE_AUDITED("trade_audited","出款审核通过"),
    ;


    private  String status;

    private  String desc;


    TradeStatusEnum (String status,String desc){
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
