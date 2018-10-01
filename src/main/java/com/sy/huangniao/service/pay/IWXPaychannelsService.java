package com.sy.huangniao.service.pay;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by huchao on 2018/9/24.
 */
public interface IWXPaychannelsService  extends IPayChannelsService {

    /**
     * 通过code获取openid
     * @param jsonObject
     * @return
     */
    public JSONObject code2Session(JSONObject jsonObject);
}
