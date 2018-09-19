package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/17.
 */
public enum AuditStatusEnum {

    WAIT_AUDIT("wait_audit","审核中"),
   // WAIT_AUDIT
   ;

    private  String status;

    private  String desc;


    AuditStatusEnum (String status,String desc){
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
