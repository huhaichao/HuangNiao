package com.sy.huangniao.common.bo;

import lombok.Data;

/**
 * Created by huchao on 2018/10/18.
 */
@Data
public class WXReturnRespondBody {
    //微信订单号
    private String transaction_id;
    //商户订单号
    private String out_trade_no ;
    //微信退款单号
    private String refund_id;
    //商户退款单号
    private String out_refund_no;
    //订单金额
    private String total_fee;
    //应结订单金额
    private String settlement_total_fee;
    //申请退款金额
    private String refund_fee;
    //退款金额
    private String settlement_refund_fee;
    //退款成功时间
    private String success_time;
    //退款入账账户
    private String refund_recv_accout;
    //退款资金来源
    private String refund_account;
    //退款发起来源
    private String refund_request_source;

}
