package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/15.
 */
public enum OrderStatusEnum {

    WAITROB("wait_rob","等待抢单"),
    CANCEL("cancel","订单取消"),
    ROBING("robing","抢票中"),
    ORDER_AUDIT("order_audit","出票审核中"),
    SUCCESS("success","已出票"),
    ROB_FAIL("rob_fail","抢单失败"),
    RETURN("return","退票中"),
    RETURN_SUCCESS("return_success","退票完成"),
    RETURN_FAIL("return_fail","退票失败");


    private  String status;

    private  String desc;


    OrderStatusEnum (String status,String desc){
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
