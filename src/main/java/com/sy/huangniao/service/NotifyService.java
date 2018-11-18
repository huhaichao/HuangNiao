package com.sy.huangniao.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sy.huangniao.pojo.Notify;
import com.sy.huangniao.pojo.RobOrder;

/**
 * Created by huchao on 2018/11/18.
 */
public interface NotifyService {

    /**
     * 保存
     * @param notify
     * @return
     */
    public int save(Notify notify);
    
    
    public List<Notify> selectLIst (JSONObject jsonObject);

    public int update(Notify notifyUpdate);
}
