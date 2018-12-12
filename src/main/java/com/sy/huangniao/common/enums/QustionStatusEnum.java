package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/12/12.
 */
public enum QustionStatusEnum {

    WAITHANDLE("wait_handle","问题待处理");

    private  String status;

    private  String desc;


    QustionStatusEnum (String status,String desc){
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
