package com.sy.huangniao.service;

import com.sy.huangniao.common.bo.UserInfoBody;
import com.sy.huangniao.pojo.UserInfo;

import java.util.Map;

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
     * 注册服务
     * @param userInfo
     * @return
     */
    public  UserInfoBody  registry(UserInfo userInfo);

    /**
     * 登陆服务
     * @return
     */
    public  UserInfoBody login (UserInfo userInfo);

    /**
     * 获取用户信息
     * @param
     * @return
     */
    public UserInfoBody  getUserInfo(Integer userId);

    /**
     * 实名认证接口
     * @param userId
     * @param userName
     * @param userIdentity
     * @param identityImage
     * @return
     */
    public  boolean  realName (Integer userId,String userName,String userIdentity,String identityImage);


    /**
     * 抢单或者下单
     * @param m
     * @param
     * @return
     */
    public  boolean createOrder(Map<String,String> m);


    /**
     * 获取订单列表
     * @param
     * @param m
     * @return
     */
    public  String getOrderList(Map<String,String> m);


    /**
     * 订单确认接口  --- 用户确认支付  --- 服务商确认抢到票
     * @param m
     * @param
     * @return
     */
    public  boolean  confirmeOrder (Map<String,String> m);

    /**
     * 取消订单
     * @param userId
     * @param orderId
     * @param
     * @return
     */
    public  boolean  cancleOrder (int userId ,int orderId);

}
