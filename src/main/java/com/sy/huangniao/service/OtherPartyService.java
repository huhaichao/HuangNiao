package com.sy.huangniao.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by huchao on 2018/10/3.
 *
 * 调用第三方服务接口
 *
 */
public interface OtherPartyService {

    /**
     * 实名认证接口
     * @return
     */
    public  JSONObject  realName (JSONObject jsonObject);




    /**
     * 发送验证码
     * @param jsonObject
     * @return
     */
    public  boolean  sendPhoneCode(JSONObject jsonObject,String content,boolean batchSend);

}
