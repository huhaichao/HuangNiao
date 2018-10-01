package com.sy.huangniao.service.pay;


import com.alibaba.fastjson.JSONObject;

/**
 *
 * Created by huchao on 2018/9/23.
 * 支付通道接口
 *
 */
public interface IPayChannelsService {

    /**
     * 支付通道
     * @return
     */
    public String  ChannelsName();


    /**
     *
     * 获取第三方调用token
     * @return
     */
    public String  applyToken(JSONObject jsonObject);

    /**
     * 统一下单接口
     * @param jsonObject
     * @return
     */
    public JSONObject  unifiedorder(JSONObject jsonObject);


    /**
     * 订单支付查询接口
     * @param jsonObject
     * @return
     */
    public JSONObject  orderquery(JSONObject jsonObject);


    /**
     * 关闭订单接口
     * @param jsonObject
     * @return
     */
    public JSONObject  closeorder(JSONObject jsonObject);


    /**
     * 退款接口
     * @param jsonObject
     * @return
     */
    public JSONObject  refund(JSONObject jsonObject);


    /**
     * 退款查询接口
     * @param jsonObject
     * @return
     */
    public JSONObject  refundquery(JSONObject jsonObject);


    /**
     * 回调通知接口
     * @param jsonObject
     * @return
     */
    public JSONObject  callback(JSONObject jsonObject);


    /**
     * 企业向用户付款 -- 可以用于提现
     * @param jsonObject
     * @return
     */
    public JSONObject transfers(JSONObject jsonObject);


    /**
     * 获取交易类型
     * @return
     */
    public String   getTradeType();

}
