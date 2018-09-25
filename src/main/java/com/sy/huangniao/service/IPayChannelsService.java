package com.sy.huangniao.service;

import net.sf.json.JSONObject;

import java.util.Map;

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
    public String  applyToken(Map<String,Object> map);

    /**
     * 统一下单接口
     * @param map
     * @return
     */
    public JSONObject  unifiedorder(Map<String,Object> map);


    /**
     * 订单支付查询接口
     * @param map
     * @return
     */
    public JSONObject  orderquery(Map<String,Object> map);


    /**
     * 关闭订单接口
     * @param map
     * @return
     */
    public JSONObject  closeorder(Map<String,Object> map);


    /**
     * 退款接口
     * @param map
     * @return
     */
    public JSONObject  refund(Map<String,Object> map);


    /**
     * 退款查询接口
     * @param map
     * @return
     */
    public JSONObject  refundquery(Map<String,Object> map);


    /**
     * 回调通知接口
     * @param map
     * @return
     */
    public JSONObject  callback(Map<String,Object> map);


    /**
     * 企业向用户付款 -- 可以用于提现
     * @param map
     * @return
     */
    public JSONObject transfers(Map<String,Object> map);

}
