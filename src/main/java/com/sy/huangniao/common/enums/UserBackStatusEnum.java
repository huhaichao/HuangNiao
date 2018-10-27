package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/10/27.
 */
public enum  UserBackStatusEnum {

    WAIT_HANDLE("wait_handle","成功");


    private  String status;

    private  String desc;


    UserBackStatusEnum (String status,String desc){
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
