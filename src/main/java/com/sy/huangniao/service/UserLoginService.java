package com.sy.huangniao.service;

import com.sy.huangniao.common.enums.AppCodeEnum;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
 */
public interface UserLoginService {

    public AppCodeEnum getAppCode();
    /**
     * 注册服务
     * @param
     * @return
     */
    public JSONObject registry(Map<String,String> map);

    /**
     * 登陆服务
     * @return
     */
    public  JSONObject login (Map<String,String> map);
}
