package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/15.
 */
public enum OrderStatusEnum {

    WAITPAY("wait_pay","等待支付"),
    WAITROB("wait_rob","等待抢单"),
    CANCEL("cancel","订单取消"),
    RETURNING_AMOUNT("returning_amount","退款中"),
    RETURNING_CHANNELS("returning_channels","第三放支付通道退款中"),
    RETURNED_AMOUNT("returned_amount","已退款"),
    RETURNED_AUDIT("returned_audit","退款审核中"),
    RETURNED_AMOUNT_FAIL("returned_amount_fail","退款失败--具体原因看备注"),
    RETURNED_RETURN("returned_return","退款审核驳回,在备注中写明驳回原因"),
    ROBING("robing","抢票中"),
    ORDER_AUDIT("order_audit","出票审核中"),
    TICKET_SUCCESS("ticket_success","已支付--等待用户确认"),
    USER_PAY("user_pay","用户付款中"),
    SUCCESS("success","已完成"),
    ROB_FAIL("rob_fail","抢单失败"),
    WAITCONFIRME("wait_confirme","等待确认"),
    RETURN("return","退票中"),
    RETURN_SUCCESS("return_success","退票完成"),
    RETURN_FAIL("return_fail","退票失败"),

    ;

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
