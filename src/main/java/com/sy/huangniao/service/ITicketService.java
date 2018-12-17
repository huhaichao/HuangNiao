package com.sy.huangniao.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by huchao on 2018/12/15.
 */
public interface ITicketService {

    /**
     * 初始化票务服务
     */
    public void initSite();

    /**
     *  获取站点信息
      * @param jsonObject
     * @return
     */
    public List<JSONObject> getSiteList(JSONObject jsonObject);

    /**
     *
     * 获取车票信息列表
     * @param jsonObject
     * @return
     */
    public JSONArray getTicketInfoList(JSONObject jsonObject);

}
