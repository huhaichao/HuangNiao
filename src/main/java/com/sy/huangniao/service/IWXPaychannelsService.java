package com.sy.huangniao.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
 */
public interface IWXPaychannelsService  extends IPayChannelsService{

    /**
     * 通过code获取openid
     * @param map
     * @return
     */
    public JSONObject  code2Session(Map<String,String> map);
}
