package com.sy.huangniao.service;

import com.sy.huangniao.common.enums.AppCodeEnum;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
 * 分应用业务处理
 */
public interface UserAppService {

    public AppCodeEnum getAppCode();
    /**
     * 注册服务
     * @param
     * @return
     */
    public JSONObject registry(JSONObject jsonObject);

    /**
     * 登陆服务
     * @return
     */
    public  JSONObject login (JSONObject jsonObject);

    /**
     * 创建账户号
     * @return
     */
    public String  createUserAcountNo();

    /**
     * 创建交易单号
     * @return
     */
    public String  createTradeNo();


    /**
     * 创建订单号
     * @return
     */
    public String  createOrderNO();

    /**
     *
     * 充值
     * @param jsonObject
     * @return
     */
    public  JSONObject  deposit(JSONObject jsonObject);


    /**
     * 充值或者提现--支付结果查询业务处理
     * @param jsonObject
     * @return
     */
    JSONObject  payQuery(JSONObject jsonObject);

    /**
     *
     * 提现接口
     * @param jsonObject
     * @return
     */
    public  JSONObject  withdraw(JSONObject jsonObject);


   /* *//**
     *
     * 获取第三方调用token
     * @return
     *//*
    public String  applyToken(JSONObject jsonObject);

    *//**
     * 统一下单接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  unifiedorder(JSONObject jsonObject);


    *//**
     * 订单支付查询接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  orderquery(JSONObject jsonObject);


    *//**
     * 关闭订单接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  closeorder(JSONObject jsonObject);


    *//**
     * 退款接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  refund(JSONObject jsonObject);


    *//**
     * 退款查询接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  refundquery(JSONObject jsonObject);


    *//**
     * 回调通知接口
     * @param jsonObject
     * @return
     *//*
    public JSONObject  callback(JSONObject jsonObject);


    *//**
     * 企业向用户付款 -- 可以用于提现
     * @param jsonObject
     * @return
     *//*
    public JSONObject transfers(JSONObject jsonObject);
    */
}
