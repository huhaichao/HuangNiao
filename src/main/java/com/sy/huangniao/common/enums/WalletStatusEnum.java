package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/28.
 */
public enum WalletStatusEnum {

    SUCCESS("success","成功"),
    DEPOSITING("depositing","充值中"),
    WITHDRAWING("withdrawing","提现中"),
    CANCEL("cancel","取消"),
    ;


    private  String status;

    private  String desc;


    WalletStatusEnum (String status,String desc){
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
