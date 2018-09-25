package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/23.
 */
public enum AppCodeEnum {

    XCX("XCX","小程序");

    private  String code;

    private  String desc;


    AppCodeEnum (String code,String desc){
        this.code=code;
        this.desc=desc;
    }


    public String getCode(){
        return  this.code;
    }

    public String getDesc(){
        return  this.desc;
    }
}
