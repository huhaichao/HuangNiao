package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/27.
 */
public enum ChannelTradeTypeEnum {

    JSAPI("JSAPI","公众号支付"),
    NATIVE("NATIVE","原生扫码支付"),
    APP("APP","app支付");

    private  String tradeType;

    private  String desc;


    ChannelTradeTypeEnum (String role,String desc){
        this.tradeType=role;
        this.desc=desc;
    }


    public String getTradeType(){
        return  this.tradeType;
    }

    public String getDesc(){
        return  this.desc;
    }
}
