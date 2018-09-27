package com.sy.huangniao.service;

import com.sy.huangniao.common.enums.AppCodeEnum;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
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
}
