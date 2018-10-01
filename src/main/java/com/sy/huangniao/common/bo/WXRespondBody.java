package com.sy.huangniao.common.bo;

import lombok.Data;

/**
 * Created by huchao on 2018/9/29.
 */
@Data
public class WXRespondBody {

    //此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private  String  return_code ;

    //返回信息，如非空，为错误原因
    private  String  return_msg ;

    //微信分配的小程序ID
    private  String appid;

    //微信支付分配的商户号
    private  String mch_id;

    //微信支付分配的终端设备号，
    private  String device_info ;

    //签名
    private  String   sign ;

    //签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
    private String  sign_type;

    //业务结果
    private String result_code;

    //错误代码
    private String  err_code;

    //错误代码描述
    private String  err_code_des;

    //用户标识
    private String openid;

    //是否关注公众账号
    private String  is_subscribe;

    //交易类型
    private String trade_type ;

    //付款银行
    private String  bank_type;

    //订单金额 单位分
    private int total_fee;

    //应结订单金额 单位分
    private int settlement_total_fee;

    //货币种类
    private  String  fee_type;

    //现金支付金额 单位分
    private  int  cash_fee;

    //现金支付货币类型
    private String cash_fee_type;

    //微信支付订单号
    private String transaction_id ;

    //商户订单号
    private String out_trade_no ;

    //商家数据包
    private String  attach;

    //支付完成时间
    private String  time_end ;


}
