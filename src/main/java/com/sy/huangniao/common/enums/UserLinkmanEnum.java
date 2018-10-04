package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/10/4.
 */
public enum UserLinkmanEnum {
    NORMAL("normal","正常");


    private  String status;

    private  String desc;


    UserLinkmanEnum (String status,String desc){
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
