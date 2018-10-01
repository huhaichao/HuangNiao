package com.sy.huangniao.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.pojo.UserInfo;

/**
 * Created by huchao on 2018/9/14.
 *
 * 用户服务接口
 */
public interface UserInfoService {

    /**
     * 获取用户角色
     * @return
     */
    public String getUserRole();

    /**
     * 创建用户
     * @param jsonObject
     * @return
     */
    public UserInfo   createUserInfo(JSONObject jsonObject);

    /**
     * 获取用户信息
     * @param
     * @return
     */
    public UserInfoBody  getUserInfo(Integer userId);


    /**
     * 修改用户信息
     * @param jsonObject
     * @return
     */
    public  UserInfoBody updateUserInfo(JSONObject jsonObject);

    /**
     * 实名认证接口
     * @return
     */
    public  boolean  realName (JSONObject jsonObject);


    /**
     * 抢单或者下单
     * @param
     * @param
     * @return
     */
    public  boolean createOrder(JSONObject jsonObject);

    /**
     * 获取订单列表
     * @param
     * @param jsonObject
     * @return
     */
    public  String getOrderList(JSONObject jsonObject);


    /**
     * 订单确认接口  --- 用户确认支付  --- 服务商确认抢到票
     * @param jsonObject
     * @param
     * @return
     */
    public  boolean  confirmeOrder (JSONObject jsonObject);

    /**
     * 取消订单
     * @param userId
     * @param orderId
     * @param
     * @return
     */
    public  boolean  cancleOrder (int userId ,int orderId);

    /**
     *
     * 充值
     * @param jsonObject
     * @return
     *//*
    public  JSONObject  deposit(JSONObject jsonObject);


    *//**
     * 充值或者提现--支付结果查询业务处理
     * @param jsonObject
     * @return
     *//*
    JSONObject  payQuery(JSONObject jsonObject);

    *//**
     * 
     * 提现接口
     * @param jsonObject
     * @return
     *//*
    public  JSONObject  withdraw(JSONObject jsonObject);*/



}
