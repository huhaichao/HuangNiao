package com.sy.huangniao.config.wx;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.sy.huangniao.common.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by huchao on 2018/9/27.
 */
@Component
public class WxPayConfig extends WXPayConfig {

    @Autowired
    private Constant constant;

    @Override
    public String getAppID() {
        return constant.getWX_XCX_APPID();
    }

    @Override
    public String getMchID() {
        return constant.getWX_XCX_MCH_ID();
    }

    @Override
    public String getKey() {
        return constant.getWX_XCX_MCH_KEY();
    }

    @Override
    public InputStream getCertStream() {
        FileInputStream instream = null;
        try {
            instream = new FileInputStream(new File(constant.getCertPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  instream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }
}
