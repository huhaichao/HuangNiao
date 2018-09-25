package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/25.
 */
public enum  UserAccountStatusEnum {

    NORMAL("normal","正常"),
    COOL("cool","冻结");


    private  String status;

    private  String desc;


    UserAccountStatusEnum (String status,String desc){
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
