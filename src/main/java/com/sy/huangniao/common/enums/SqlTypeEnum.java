package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/15.
 */
public enum SqlTypeEnum {

    DEAFULT("deafult","默认执行"),
    UPDATEBYUSERID("updateByUserId","通过用户id修改数据"),
    SELECTOBJECTBYSELECTIVE("selectObjectBySelective", "通过实体查询对象"),
    UPDATEBYORDERID("updateByOrderId","通过订单id来修改" ),
    UPDATEACCOUNTAMOUNT("updateAccountAmount", "修改账户金额"),
    UPDATEBYUSERIDANDORDERNO("updateByuserIdAndOrderNo","通过userId和orderNo修改数据"),
    UPADTEBYIDANDBYSTATUS("updateByIdAndStatus","修改特定状态的记录");

    private  String sqlType;

    private  String desc;


    SqlTypeEnum (String sqlType,String desc){
        this.sqlType=sqlType;
        this.desc=desc;
    }


    public String getSqlType(){
        return  this.sqlType;
    }

    public String getDesc(){
        return  this.desc;
    }
}
