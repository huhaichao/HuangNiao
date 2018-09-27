package com.sy.huangniao.service.impl.XCX;

import com.github.wxpay.sdk.WXPay;
import com.sy.huangniao.common.Util.HttpClientUtils;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.config.wx.WxPayConfig;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
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

    @Autowired
    private WxPayConfig wxPayConfig;


    @Override
    public String ChannelsName() {
        return AppCodeEnum.XCX.getCode();
    }

    @Override
    public String applyToken(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject unifiedorder(JSONObject jsonObject) {

        try {
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject orderquery(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject closeorder(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject refund(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject refundquery(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject callback(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject transfers(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject code2Session(JSONObject jsonObject) {
        Map<String,String> params =new HashMap<String,String>();
        params.put("appid",constant.getWX_XCX_APPID());
        params.put("secret",constant.getWX_XCX_APP_SECRETD());
        params.put("js_code",jsonObject.getString("code"));
        params.put("grant_type","authorization_code");
        String  result = HttpClientUtils.get(constant.getWX_XCX_URL_JSCODE2SESSION(),params,
                null,30000,30000);
        JSONObject json = net.sf.json.JSONObject.fromObject(result);
        return  json;
    }
}
