package com.sy.huangniao.service.impl.XCX;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.sy.huangniao.common.Util.HttpClientUtils;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.AppCodeEnum;
import com.sy.huangniao.common.enums.ChannelTradeTypeEnum;
import com.sy.huangniao.config.wx.WxPayConfig;
import com.sy.huangniao.service.pay.IWXPaychannelsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by huchao on 2018/9/25.
 * 小程序通道处理
 * 纯通道处理服务
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
            log.info("微信预下单接口上送参数jsonObject={}",jsonObject);
            Map<String,String> reqdata = (Map) JSON.parse(jsonObject.toJSONString());;
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
            Map<String, String> result = wxPay.unifiedOrder(reqdata);
            JSONObject resultJson = new JSONObject();
            resultJson.putAll(result);
            log.info("微信预下单接口返回结果result={}",resultJson);
            if("SUCCESS".equals(resultJson.getString("return_code"))){
                if("SYSTEMERROR".equals(resultJson.getString("err_code"))){
                   log.info("系统异常unifiedorder重试一次");
                    Map<String, String> result2=  wxPay.unifiedOrder(reqdata);
                    resultJson.clear();
                    resultJson.putAll(result2);
                }
            }
            return  resultJson;
        } catch (Exception e) {
            log.info("调用微信预下单接口失败={}",e.getMessage());
        }
        return null;
    }

    @Override
    public JSONObject orderquery(JSONObject jsonObject) {
        try {
            log.info("微信查询接口上送参数jsonObject={}",jsonObject);
            Map<String,String> reqdata = (Map)JSON.parse(jsonObject.toJSONString());;
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
            Map<String, String> result = wxPay.orderQuery(reqdata);
            JSONObject resultJson = new JSONObject();
            resultJson.putAll(result);
            log.info("微信查询接口返回结果result={}",resultJson);
            return  resultJson;
        } catch (Exception e) {
            log.info("调用微信查询接口失败={}",e.getMessage());
        }
        return null;
    }

    @Override
    public JSONObject closeorder(JSONObject jsonObject) {
        try {
            log.info("微信订单关闭接口上送参数jsonObject={}",jsonObject);
            Map<String,String> reqdata = (Map)JSON.parse(jsonObject.toJSONString());;
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
            Map<String, String> result = wxPay.closeOrder(reqdata);
            JSONObject resultJson = new JSONObject();
            resultJson.putAll(result);
            log.info("微信订单关闭接口返回结果result={}",resultJson);
            return  resultJson;
        } catch (Exception e) {
            log.info("调用微信订单关闭接口失败={}",e.getMessage());
        }
        return null;
    }

    @Override
    public JSONObject refund(JSONObject jsonObject) {
        try {
            log.info("微信退款接口上送参数jsonObject={}",jsonObject);
            Map<String,String> reqdata = (Map)JSON.parse(jsonObject.toJSONString());;
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
            Map<String, String> result = wxPay.refund(reqdata);
            JSONObject resultJson = new JSONObject();
            resultJson.putAll(result);
            log.info("微信退款接口返回结果result={}",resultJson);
            return  resultJson;
        } catch (Exception e) {
            log.info("调用退款接口失败={}",e.getMessage());
        }
        return null;
    }

    @Override
    public JSONObject refundquery(JSONObject jsonObject) {
        try {
            log.info("微信退款查询接口上送参数jsonObject={}",jsonObject);
            Map<String,String> reqdata = (Map)JSON.parse(jsonObject.toJSONString());;
            WXPay wxPay = new WXPay(wxPayConfig,constant.getWX_XCX_URL_NOTIFY(),constant.getWX_AUTOREPORT(),constant.getWX_USESENDBOX());
            Map<String, String> result = wxPay.refund(reqdata);
            JSONObject resultJson = new JSONObject();
            resultJson.putAll(result);
            log.info("微信退款查询接口返回结果result={}",resultJson);
            return  resultJson;
        } catch (Exception e) {
            log.info("调用退款查询接口失败={}",e.getMessage());
        }
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
    public String getTradeType() {
        return ChannelTradeTypeEnum.JSAPI.getTradeType();
    }

    @Override
    public JSONObject code2Session(JSONObject jsonObject) {
        log.info(" code2Session ");
        Map<String,String> params =new HashMap<String,String>();
        params.put("appid",constant.getWX_XCX_APPID());
        params.put("secret",constant.getWX_XCX_APP_SECRETD());
        params.put("js_code",jsonObject.getString("code"));
        params.put("grant_type","authorization_code");
        String  result = HttpClientUtils.get(constant.getWX_XCX_URL_JSCODE2SESSION(),params,
                null,30000,30000);
        JSONObject json =(JSONObject)JSONObject.parseObject(result);
        return  json;
    }
}
