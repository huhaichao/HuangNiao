package com.sy.huangniao.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by huchao on 2018/9/24.
 */
@Component
public class Constant {

    /**
     * redis key的规则
     *
     * 前缀+appCode+业务属性
     */
    public static final String CACHELOGINKEY ="USERLOGINKEY" ;
    public static final String GETUSERIDBYOPENID ="USEROPENID" ;
    public static final String USERIDSESSIONKEY ="USERIDSESSIONKEY" ;

    /**
     * 小程序账户前缀
     */
    public static final String USERACCOUNTXCX ="10" ;

    //订单号前缀
    public static final String ORDERNOPRFIX ="88";
    //交易单号前缀
    public static final String TRADENOPREFIX ="99" ;

    //失效时间37*24*3600
    public static final long   loginKeyexprirTime=3196800;




    @Value("${wx.xcx.appid}")
    private String WX_XCX_APPID;
    public String  getWX_XCX_APPID(){
        return  this.WX_XCX_APPID;
    }
    @Value("${wx.xcx.mch_id}")
    private String WX_XCX_MCH_ID;
    public String  getWX_XCX_MCH_ID(){
        return  this.WX_XCX_MCH_ID;
    }
    @Value("${wx.xcx.app_secret}")
    private String WX_XCX_APP_SECRET;
    public String  getWX_XCX_APP_SECRETD(){
        return  this.WX_XCX_APP_SECRET;
    }
    @Value("${wx.xcx.url.jscode2session}")
    private String WX_XCX_URL_JSCODE2SESSION;
    public String  getWX_XCX_URL_JSCODE2SESSION(){
        return  this.WX_XCX_URL_JSCODE2SESSION;
    }
    @Value("${wx.xcx.url.notify}")
    private String WX_XCX_URL_NOTIFY;
    public String  getWX_XCX_URL_NOTIFY(){
        return  this.WX_XCX_URL_NOTIFY;
    }

}
