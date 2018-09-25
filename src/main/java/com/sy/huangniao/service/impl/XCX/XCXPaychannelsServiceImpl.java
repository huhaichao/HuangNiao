package com.sy.huangniao.service.impl.XCX;

import com.sy.huangniao.common.Util.HttpClientUtils;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.service.IWXPaychannelsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by huchao on 2018/9/25.
 * 小程序通道处理
 *
 */
@Slf4j
@Component
public class XCXPaychannelsServiceImpl implements IWXPaychannelsService {

    @Autowired
    private Constant constant;


    @Override
    public String ChannelsName() {
        return AppCodeEnum.XCX.getCode();
    }

    @Override
    public String applyToken(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject unifiedorder(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject orderquery(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject closeorder(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject refund(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject refundquery(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject callback(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject transfers(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject code2Session(Map<String, String> map) {
        Map<String,String> params =new HashMap<String,String>();
        params.put("appid",constant.getWX_XCX_APPID());
        params.put("secret",constant.getWX_XCX_APP_SECRETD());
        params.put("js_code",map.get("code"));
        params.put("grant_type","authorization_code");
        String  result = HttpClientUtils.get(constant.getWX_XCX_URL_JSCODE2SESSION(),params,
                null,30000,30000);
        JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
        return  jsonObject;
    }
}
