package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/14.
 *
 * 用户角色
 */
public enum UserRoleEnum {

    CUSTOMER("customer","客户"),
    BUSINESS("business","商户"),
    ADMIN("admin","管理员由系统内部分配，不提供注册接口，具备审核资格的用户");

    private  String role;

    private  String desc;


    UserRoleEnum (String role,String desc){
        this.role=role;
        this.desc=desc;
    }


    public String getRole(){
        return  this.role;
    }

    public String getDesc(){
        return  this.desc;
    }
}
