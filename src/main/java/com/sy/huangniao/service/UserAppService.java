package com.sy.huangniao.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.enums.AppCodeEnum;

import javax.servlet.http.HttpServletRequest;


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
     * 创建充值单号
     * @return
     */
    public String  createDepositNO();

    /**
     * 创建订单号
     * @return
     */
    public String  createReturnNO();

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


    /**
     * 退款接口
     */
    public  JSONObject  returned(JSONObject jsonObject);

    /**
     *  支付结果回调接口
     * @param request
     * @return
     */
    public  String payCallback(HttpServletRequest request);

    /**
     *  退款通知接口
     * @param request
     * @return
     */
    public String returnCallback(HttpServletRequest request);


    /**
     * 支付订单
     * @param jsonObject
     * @return
     */
    public  JSONObject payOrder(JSONObject jsonObject);
}
