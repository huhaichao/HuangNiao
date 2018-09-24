package com.sy.huangniao.common.enums;

/**
 * Created by huchao on 2018/9/14.
 * 返回报文
 *
 */
public enum RespondMessageEnum {


    SUCCESS("0000","成功"),
    SAVEUSERINFOERROR("0001","用户保存信息失败"),
    PASSWORDOERROR("0002", "密码错误"),
    UPDATEUSERINFOERROR("0003","修改用户信息失败"),
    CREATROBORDERFAIL("0004", "商户抢单失败"),
    CREATORDERFAIL("0005","商户下单失败"),
    NO_LOGIN("0006","用户未登陆"),
    NOINFO_LOGINKEY("0007", "缺少loginkey"),
    NOINFO_USERID("0008","缺少userId" );

    private  String  code;

    private  String  msg ;

    RespondMessageEnum(String code , String msg){
        this.code =code;
        this.msg =msg;
    }

    public String getCode(){
        return  this.code;
    }

    public String getMsg(){
        return  this.msg;
    }
}
