package com.sy.huangniao.service.pay;

import com.sy.huangniao.service.pay.IPayChannelsService;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by huchao on 2018/9/24.
 */
public interface IWXPaychannelsService  extends IPayChannelsService {

    /**
     * 通过code获取openid
     * @param map
     * @return
     */
    public JSONObject  code2Session(Map<String,String> map);
}
