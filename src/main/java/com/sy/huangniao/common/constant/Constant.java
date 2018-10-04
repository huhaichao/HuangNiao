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
    //第三方调用小程序前缀
    public static final String ERRORCODEXCX ="WX_CODE_" ;
    
    public static final String USERIDSESSIONKEY ="USERIDSESSIONKEY" ;
    //校验手机号前缀
    public static final String CACHEPHONECODE ="CACHEPHONECODE" ;

    /**
     * 登陆签名key
     */
    @Value("${user.login.sign.key}")
    private String USERLOGINSIGNKEY;
    public  String getUSERLOGINSIGNKEY(){
        return  this.USERLOGINSIGNKEY;
    }

    /**
     * 小程序账户前缀
     */
    @Value("${user.account.xcx}")
    private  String USERACCOUNTXCX  ;
    public  String getUSERACCOUNTXCX(){
        return  this.USERACCOUNTXCX;
    }
    //订单号前缀
    @Value("${user.orderno.xcx}")
    private  String ORDERNOXCX ;
    public  String getORDERNOXCX(){
        return  this.ORDERNOXCX;
    }
    //交易单号前缀
    @Value("${user.tradeno.xcx}")
    private   String TRADENOXCX  ;
    public  String getTRADENOXCX(){
        return  this.TRADENOXCX;
    }
    //失效时间37*24*3600
    @Value("${user.loginKeyexprirTime}")
    private   long   loginKeyexprirTime;
    public  long getLoginKeyexprirTime(){
        return  this.loginKeyexprirTime;
    }

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
    @Value("${wx.autoReport}")
    private static  boolean WX_AUTOREPORT  ;
    public boolean getWX_AUTOREPORT() {
        return this.WX_AUTOREPORT;
    }
    @Value("${wx.useSendBox}")
    private static  boolean WX_USESENDBOX  ;
    public boolean getWX_USESENDBOX() {
        return this.WX_USESENDBOX;
    }


    //短信通道
    @Value("${http.sms.url}")
    private String SMS_URL;
    @Value("${http.sms.account}")
    private String SMS_ACCOUNT;
    @Value("${http.sms.password}")
    private String SMS_PWD;
    @Value("${http.sms.content}")
    private String SMS_CONTENT;
    @Value("${http.sms.needstatus}")
    private String SMS_NEEDSTATUS;
    @Value("${http.sms.product}")
    private String SMS_PRODUCT;
    @Value("${http.sms.extno}")
    private String SMS_EXTNO;
    @Value("${http.sms.exprirTime}")
    private long SMS_ExprirTime;

    //实名认证通道
    @Value("${realName.url}")
    private String REALNAME_URL;
    @Value("${realName.appCode}")
    private String REALNAME_APPCODE;


    public String getSMS_URL() {
        return SMS_URL;
    }

    public String getSMS_ACCOUNT() {
        return SMS_ACCOUNT;
    }

    public String getSMS_PWD() {
        return SMS_PWD;
    }

    public String getSMS_CONTENT() {
        return SMS_CONTENT;
    }

    public String getSMS_NEEDSTATUS() {
        return SMS_NEEDSTATUS;
    }

    public String getSMS_PRODUCT() {
        return SMS_PRODUCT;
    }

    public String getSMS_EXTNO() {
        return SMS_EXTNO;
    }

    public long getSMS_ExprirTime() {
        return SMS_ExprirTime;
    }

    public String getREALNAME_URL() {
        return REALNAME_URL;
    }

    public String getREALNAME_APPCODE() {
        return REALNAME_APPCODE;
    }
}
