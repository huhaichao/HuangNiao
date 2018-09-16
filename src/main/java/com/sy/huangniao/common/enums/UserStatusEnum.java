package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/14.
 */
public enum  UserStatusEnum {

    WAITAUTHEN("wait_authen","等待认证"),
    AUTHENED("authened","认证成功"),
    COOL("cool","冻结");


    private  String status;

    private  String desc;


    UserStatusEnum (String status,String desc){
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
